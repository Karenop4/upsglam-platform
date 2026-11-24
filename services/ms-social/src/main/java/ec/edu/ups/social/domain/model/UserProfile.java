/**
 * Capa: Domain
 * Responsabilidad: Entidad de dominio del perfil de usuario vinculado a
 *   Supabase Auth.
 * Regla de Dependencia: CERO dependencias de Spring, R2DBC, Jackson o cualquier
 *   framework externo. Solo puede usar tipos de la librería estándar de Java
 *   (java.util, java.time) y otras clases del paquete domain.model.
 */
package ec.edu.ups.social.domain.model;

import java.time.Instant;
import java.util.UUID;

public record UserProfile(
        UUID id,
        String username,
        String avatarUrl,
        Instant createdAt
) {
}
