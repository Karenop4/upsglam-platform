/**
 * Capa: Infrastructure (Web DTO)
 * Responsabilidad: Objetos de transferencia de datos validados para solicitudes
 *   de creación o actualización de publicaciones referenciando
 *   processingRequestId.
 * Regla de Dependencia: Puede usar anotaciones de Jakarta Validation y Jackson.
 *   Solo es utilizado por la capa Infrastructure (PostController) y mapeado a
 *   entidades de dominio antes de cruzar hacia Application/Domain. Prohibido
 *   su uso dentro de Domain o Application.
 */
package ec.edu.ups.social.infrastructure.adapter.in.web.dto;

import java.util.UUID;

public record PostRequestDto(
        UUID processingRequestId,
        String imageUrl,
        String caption
) {
}
