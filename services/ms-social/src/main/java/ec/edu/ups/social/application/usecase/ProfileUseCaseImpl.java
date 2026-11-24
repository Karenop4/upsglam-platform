/**
 * Capa: Application (Use Case Implementation)
 * Responsabilidad: Lógica de negocio para consultar y actualizar el perfil del
 *   usuario autenticado validando que solo pueda modificar su propio perfil.
 * Regla de Dependencia: Implementa ProfileUseCasePort y se comunica hacia
 *   afuera únicamente mediante ProfileRepositoryPort. Prohibido importar
 *   controladores web, DTOs o clientes HTTP de Infrastructure.
 */
package ec.edu.ups.social.application.usecase;

import ec.edu.ups.social.domain.port.in.ProfileUseCasePort;
import ec.edu.ups.social.domain.port.out.ProfileRepositoryPort;

public class ProfileUseCaseImpl implements ProfileUseCasePort {

    private final ProfileRepositoryPort profileRepositoryPort;

    public ProfileUseCaseImpl(ProfileRepositoryPort profileRepositoryPort) {
        this.profileRepositoryPort = profileRepositoryPort;
    }
}
