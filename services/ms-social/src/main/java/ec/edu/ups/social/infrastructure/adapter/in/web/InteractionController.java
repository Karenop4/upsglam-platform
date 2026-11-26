/**
 * Capa: Infrastructure (Inbound Web / Realtime Adapter)
 * Responsabilidad: Endpoints REST y SSE/WebSocket para likes y comentarios en
 *   tiempo real.
 * Regla de Dependencia: Se comunica hacia adentro exclusivamente mediante
 *   InteractionUseCasePort y utiliza tipos de Spring WebFlux / Reactor. Tiene
 *   prohibido acceder directamente a adaptadores de base de datos R2DBC.
 */
package ec.edu.ups.social.infrastructure.adapter.in.web;

import ec.edu.ups.social.domain.port.in.InteractionUseCasePort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/social/posts")
public class InteractionController {

    private final InteractionUseCasePort interactionUseCasePort;

    public InteractionController(InteractionUseCasePort interactionUseCasePort) {
        this.interactionUseCasePort = interactionUseCasePort;
    }
}
