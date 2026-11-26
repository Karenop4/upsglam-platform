/**
 * Capa: Infrastructure (API Gateway Security - JWT Decoding)
 * Responsabilidad: Construir el ReactiveJwtDecoder que valida los JWT de
 *   Supabase Auth:
 *   1. Primario: firma asimétrica (ES256/RS256) con claves del endpoint JWKS,
 *      cacheadas y refrescadas por Spring Security / Nimbus sin bloquear.
 *   2. Opcional: HS256 con el secreto legado, SOLO si SUPABASE_JWT_SECRET está
 *      definido. Cada decoder acepta exclusivamente su propio algoritmo, por
 *      lo que el header 'alg' del token nunca elige la clave (sin confusión de
 *      algoritmos).
 *   Ambos validan exp/nbf (con tolerancia de reloj), iss, aud y sub.
 * Regla de Dependencia: Depende de Spring Security OAuth2 Resource Server.
 *   Tiene prohibido conocer la lógica de negocio de los microservicios.
 */
package ec.edu.ups.gateway.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import ec.edu.ups.gateway.config.SupabaseSecurityProperties;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import reactor.core.publisher.Mono;

@Configuration
public class SupabaseJwtDecoderConfig {

    private static final Logger log = LoggerFactory.getLogger(SupabaseJwtDecoderConfig.class);

    @Bean
    public ReactiveJwtDecoder supabaseJwtDecoder(SupabaseSecurityProperties props) {
        OAuth2TokenValidator<Jwt> validator = supabaseTokenValidator(props);

        NimbusReactiveJwtDecoder asymmetric = NimbusReactiveJwtDecoder
                .withJwkSetUri(props.jwksUrl())
                .jwsAlgorithms(algs -> {
                    algs.add(SignatureAlgorithm.ES256);
                    algs.add(SignatureAlgorithm.RS256);
                })
                .build();
        asymmetric.setJwtValidator(validator);

        if (!props.legacyHmacEnabled()) {
            log.info("JWT: verificación asimétrica por JWKS habilitada (ES256/RS256). HS256 deshabilitado.");
            return asymmetric;
        }

        SecretKey key = new SecretKeySpec(
                props.legacyJwtSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        NimbusReactiveJwtDecoder hmac = NimbusReactiveJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
        hmac.setJwtValidator(validator);

        log.warn("JWT: verificación HS256 legada habilitada además de JWKS. "
                + "Elimine SUPABASE_JWT_SECRET cuando todos los tokens sean asimétricos.");
        return new AlgorithmRoutingJwtDecoder(asymmetric, hmac);
    }

    static OAuth2TokenValidator<Jwt> supabaseTokenValidator(SupabaseSecurityProperties props) {
        return new DelegatingOAuth2TokenValidator<>(
                // exp + nbf con 60 s de tolerancia de reloj, más iss exacto
                JwtValidators.createDefaultWithIssuer(props.issuer()),
                new JwtClaimValidator<List<String>>(JwtClaimNames.AUD,
                        aud -> aud != null && aud.contains(props.audience())),
                new JwtClaimValidator<String>(JwtClaimNames.SUB,
                        sub -> sub != null && !sub.isBlank()));
    }

    /**
     * Enruta a un decoder según el 'alg' del header. Es seguro porque cada
     * decoder delegado rechaza cualquier algoritmo distinto al suyo: el header
     * solo elige a quién preguntar, nunca qué clave o algoritmo se acepta.
     */
    static final class AlgorithmRoutingJwtDecoder implements ReactiveJwtDecoder {

        private final ReactiveJwtDecoder asymmetric;
        private final ReactiveJwtDecoder hmac;

        AlgorithmRoutingJwtDecoder(ReactiveJwtDecoder asymmetric, ReactiveJwtDecoder hmac) {
            this.asymmetric = asymmetric;
            this.hmac = hmac;
        }

        @Override
        public Mono<Jwt> decode(String token) {
            final JWSAlgorithm alg;
            try {
                if (!(JWTParser.parse(token) instanceof SignedJWT signed)) {
                    return Mono.error(new BadJwtException("Solo se aceptan JWT firmados"));
                }
                alg = signed.getHeader().getAlgorithm();
            } catch (ParseException e) {
                return Mono.error(new BadJwtException("JWT mal formado", e));
            }
            return JWSAlgorithm.HS256.equals(alg) ? hmac.decode(token) : asymmetric.decode(token);
        }
    }
}
