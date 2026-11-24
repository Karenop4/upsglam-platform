/**
 * Capa: Infrastructure (Web DTO)
 * Responsabilidad: Objetos de transferencia de datos validados para respuestas
 *   HTTP de publicaciones en el feed social referenciando processingRequestId.
 * Regla de Dependencia: Pertenece exclusivamente al adaptador web de
 *   Infrastructure (PostController). Tiene prohibido filtrarse hacia las capas
 *   Application o Domain.
 */
package ec.edu.ups.social.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public record PostResponseDto(
        UUID id,
        UUID userId,
        UUID processingRequestId,
        String imageUrl,
        String caption,
        Instant createdAt
) {
}
