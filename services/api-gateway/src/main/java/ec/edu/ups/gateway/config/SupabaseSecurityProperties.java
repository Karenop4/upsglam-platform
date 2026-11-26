/**
 * Capa: Infrastructure (API Gateway Configuration Properties)
 * Responsabilidad: Configuración tipada y validada de la verificación de JWT
 *   emitidos por Supabase Auth (JWKS, issuer, audience y secreto HS256 legado
 *   opcional). Si falta un valor obligatorio el Gateway falla al arrancar en
 *   lugar de responder 401 silenciosamente.
 * Regla de Dependencia: Solo depende de Spring Boot y Jakarta Validation. No
 *   conoce la lógica de los microservicios internos.
 */
package ec.edu.ups.gateway.config;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "upsglam.security.supabase")
public record SupabaseSecurityProperties(
        @NotBlank String jwksUrl,
        @NotBlank String issuer,
        @NotBlank String audience,
        String legacyJwtSecret) {

    /** Longitud mínima exigida por HS256 (RFC 7518 §3.2): 256 bits. */
    private static final int MIN_HMAC_SECRET_BYTES = 32;

    public boolean legacyHmacEnabled() {
        return legacyJwtSecret != null && !legacyJwtSecret.isBlank();
    }

    @AssertTrue(message = "SUPABASE_JWT_SECRET debe tener al menos 32 bytes si se define")
    public boolean isLegacyJwtSecretStrongEnough() {
        return !legacyHmacEnabled()
                || legacyJwtSecret.getBytes(StandardCharsets.UTF_8).length >= MIN_HMAC_SECRET_BYTES;
    }
}
