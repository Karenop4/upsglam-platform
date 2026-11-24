/**
 * Capa: Domain (Inbound Port)
 * Responsabilidad: Puerto de entrada para likes y comentarios en tiempo real.
 * Regla de Dependencia: CERO dependencias de Spring o frameworks externos.
 *   Es implementado por Application (InteractionUseCaseImpl) y consumido por
 *   Infrastructure (InteractionController). Solo referencia entidades de
 *   domain.model.
 */
package ec.edu.ups.social.domain.port.in;

import ec.edu.ups.social.domain.model.Comment;
import ec.edu.ups.social.domain.model.Like;

public interface InteractionUseCasePort {
    // Esqueleto de contrato de entrada para likes y comentarios
}
