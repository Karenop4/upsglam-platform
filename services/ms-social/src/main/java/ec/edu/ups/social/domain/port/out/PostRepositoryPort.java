/**
 * Capa: Domain (Outbound Port)
 * Responsabilidad: Puerto de salida para persistencia reactiva de publicaciones.
 * Regla de Dependencia: CERO dependencias de Spring, R2DBC o drivers de base de
 *   datos. Es invocado por Application (PostUseCaseImpl) e implementado en
 *   Infrastructure (PostR2dbcAdapter). Solo utiliza entidades de domain.model.
 */
package ec.edu.ups.social.domain.port.out;

import ec.edu.ups.social.domain.model.Post;

public interface PostRepositoryPort {
    // Esqueleto de contrato de salida para persistencia de publicaciones
}
