/**
 * Capa: Domain
 * Responsabilidad: Entidad de dominio para reacciones (likes) con regla de
 *   unicidad usuario-post.
 * Regla de Dependencia: CERO dependencias de Spring, R2DBC, Jackson o cualquier
 *   framework externo. Solo puede importar clases estándar de Java y entidades
 *   de domain.model.
 */
package ec.edu.ups.social.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Like(
        UUID postId,
        UUID userId,
        Instant createdAt
) {
}
