/**
 * Capa: Domain (Inbound Port)
 * Responsabilidad: Puerto de entrada para CRUD de publicaciones propias y feed
 *   cronológico.
 * Regla de Dependencia: CERO dependencias de Spring o frameworks externos.
 *   Define el contrato que implementa la capa Application (PostUseCaseImpl) y
 *   que invoca la capa Infrastructure (PostController). Solo conoce modelos de
 *   domain.model.
 */
package ec.edu.ups.social.domain.port.in;

import ec.edu.ups.social.domain.model.Post;

public interface PostUseCasePort {
    // Esqueleto de contrato de entrada para gestión de publicaciones y feed
}
