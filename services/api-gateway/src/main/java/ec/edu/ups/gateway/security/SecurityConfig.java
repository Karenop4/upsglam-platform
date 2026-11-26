/**
 * Capa: Infrastructure (API Gateway Security - Authorization Rules)
 * Responsabilidad: Cadena de seguridad reactiva del borde:
 *   - /actuator/health/** público (healthchecks de Docker).
 *   - /api/v1/** exige JWT válido de Supabase (OAuth2 Resource Server).
 *   - Cualquier otra ruta (incluido /internal/** y el resto de actuator) se
 *     deniega.
 *   - Sin sesión, CSRF, form login ni basic auth: API stateless con Bearer.
 *   - Errores 401/403 en JSON homogéneo.
 * Regla de Dependencia: Depende de Spring Security WebFlux y del
 *   ReactiveJwtDecoder definido en SupabaseJwtDecoderConfig.
 */
package ec.edu.ups.gateway.security;

import ec.edu.ups.gateway.web.JsonErrorWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveJwtDecoder jwtDecoder,
                                                         JsonErrorWriter errorWriter) {
        ServerAuthenticationEntryPoint entryPoint = (exchange, ex) -> {
            log.warn("Autenticación rechazada {} {}: {}",
                    exchange.getRequest().getMethod(), exchange.getRequest().getPath(), ex.getMessage());
            exchange.getResponse().getHeaders().set(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
            return errorWriter.write(exchange, HttpStatus.UNAUTHORIZED, "Token ausente, inválido o expirado");
        };
        ServerAccessDeniedHandler deniedHandler = (exchange, ex) -> {
            log.warn("Acceso denegado {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getPath());
            return errorWriter.write(exchange, HttpStatus.FORBIDDEN, "Acceso denegado");
        };

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .requestCache(ServerHttpSecurity.RequestCacheSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(ex -> ex
                        .pathMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        .pathMatchers("/api/v1/**").authenticated()
                        .anyExchange().denyAll())
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder))
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(deniedHandler))
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(deniedHandler))
                .build();
    }

    /** Evita que Spring Boot genere un usuario/contraseña por defecto. */
    @Bean
    public org.springframework.security.core.userdetails.ReactiveUserDetailsService noUsers() {
        return username -> Mono.empty();
    }
}
