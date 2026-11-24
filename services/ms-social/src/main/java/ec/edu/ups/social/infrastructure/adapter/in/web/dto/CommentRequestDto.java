/**
 * Capa: Infrastructure (Web DTO)
 * Responsabilidad: Objeto de transferencia de datos validado para creación de
 *   comentarios en publicaciones.
 * Regla de Dependencia: Utilizado exclusivamente en InteractionController
 *   (capa Infrastructure) y convertido a entidad Comment antes de invocar
 *   InteractionUseCasePort. Prohibido su uso en Domain o Application.
 */
package ec.edu.ups.social.infrastructure.adapter.in.web.dto;

public record CommentRequestDto(
        String content
) {
}
