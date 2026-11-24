/**
 * Capa: Application (Use Case Implementation)
 * Responsabilidad: Lógica de negocio para crear, consultar, actualizar y
 *   eliminar publicaciones validando propiedad del usuario.
 * Regla de Dependencia: Implementa PostUseCasePort y se comunica hacia afuera
 *   únicamente a través de PostRepositoryPort. Tiene prohibido importar
 *   controladores web, DTOs de infraestructura o detalles de R2DBC/SQL.
 */
package ec.edu.ups.social.application.usecase;

import ec.edu.ups.social.domain.port.in.PostUseCasePort;
import ec.edu.ups.social.domain.port.out.PostRepositoryPort;

public class PostUseCaseImpl implements PostUseCasePort {

    private final PostRepositoryPort postRepositoryPort;

    public PostUseCaseImpl(PostRepositoryPort postRepositoryPort) {
        this.postRepositoryPort = postRepositoryPort;
    }
}
