/**
 * Capa: Domain (Outbound Port)
 * Responsabilidad: Puerto de salida para persistencia de likes y comentarios.
 * Regla de Dependencia: CERO dependencias de Spring, R2DBC o librerías de
 *   infraestructura. Es invocado por Application (InteractionUseCaseImpl) e
 *   implementado por adaptadores de persistencia en Infrastructure. Solo
 *   utiliza modelos de domain.model.
 */
package ec.edu.ups.social.domain.port.out;

import ec.edu.ups.social.domain.model.Comment;
import ec.edu.ups.social.domain.model.Like;

public interface InteractionRepositoryPort {
    // Esqueleto de contrato de salida para persistencia de likes y comentarios
}
