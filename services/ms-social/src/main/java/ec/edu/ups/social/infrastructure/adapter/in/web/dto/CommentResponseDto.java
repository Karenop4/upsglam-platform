/**
 * Capa: Infrastructure (Web DTO)
 * Responsabilidad: Objeto de transferencia de datos para respuestas HTTP y
 *   eventos de comentarios en publicaciones.
 * Regla de Dependencia: Pertenece al adaptador web de Infrastructure
 *   (InteractionController). Prohibido filtrarse hacia Application o Domain.
 */
package ec.edu.ups.social.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public record CommentResponseDto(
        UUID id,
        UUID postId,
        UUID userId,
        String content,
        Instant createdAt
) {
}
