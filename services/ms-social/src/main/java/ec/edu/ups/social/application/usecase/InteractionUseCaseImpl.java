/**
 * Capa: Application (Use Case Implementation)
 * Responsabilidad: Lógica de negocio para gestionar likes, comentarios y
 *   emisión de eventos en tiempo real.
 * Regla de Dependencia: Implementa InteractionUseCasePort y delega la
 *   persistencia a InteractionRepositoryPort. Prohibido depender de clases de
 *   la capa Infrastructure (Controllers, DTOs, R2DBC).
 */
package ec.edu.ups.social.application.usecase;

import ec.edu.ups.social.domain.port.in.InteractionUseCasePort;
import ec.edu.ups.social.domain.port.out.InteractionRepositoryPort;

public class InteractionUseCaseImpl implements InteractionUseCasePort {

    private final InteractionRepositoryPort interactionRepositoryPort;

    public InteractionUseCaseImpl(InteractionRepositoryPort interactionRepositoryPort) {
        this.interactionRepositoryPort = interactionRepositoryPort;
    }
}
