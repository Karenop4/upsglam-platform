/**
 * Capa: Domain
 * Responsabilidad: Entidad de dominio para comentarios en publicaciones.
 * Regla de Dependencia: CERO dependencias de Spring, R2DBC, Jackson o cualquier
 *   framework externo. Solo puede depender de tipos estándar de Java y del
 *   propio paquete domain.model.
 */
package ec.edu.ups.social.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Comment(
        UUID id,
        UUID postId,
        UUID userId,
        String content,
        Instant createdAt
) {
}
