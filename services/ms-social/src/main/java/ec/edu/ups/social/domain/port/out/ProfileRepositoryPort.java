/**
 * Capa: Domain (Outbound Port)
 * Responsabilidad: Puerto de salida para persistencia reactiva de perfiles de
 *   usuario (UserProfile).
 * Regla de Dependencia: CERO dependencias de Spring, WebClient o librerías de
 *   infraestructura. Es invocado por Application (ProfileUseCaseImpl) e
 *   implementado por Infrastructure (ProfilePostgrestAdapter).
 */
package ec.edu.ups.social.domain.port.out;

import ec.edu.ups.social.domain.model.UserProfile;

public interface ProfileRepositoryPort {
    // Esqueleto de contrato de salida para lectura/actualización de perfil
}
