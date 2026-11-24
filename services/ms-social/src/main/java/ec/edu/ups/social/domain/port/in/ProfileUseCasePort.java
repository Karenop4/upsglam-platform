/**
 * Capa: Domain (Inbound Port)
 * Responsabilidad: Puerto de entrada para consultar y actualizar el perfil de
 *   usuario autenticado (UserProfile).
 * Regla de Dependencia: CERO dependencias de Spring o frameworks externos.
 *   Es implementado por Application (ProfileUseCaseImpl) e invocado por
 *   Infrastructure (ProfileController). Solo referencia entidades de
 *   domain.model.
 */
package ec.edu.ups.social.domain.port.in;

import ec.edu.ups.social.domain.model.UserProfile;

public interface ProfileUseCasePort {
    // Esqueleto de contrato de entrada para consulta y edición de perfil de usuario
}
