/**
 * Capa: Infrastructure (Inbound Web Adapter)
 * Responsabilidad: Endpoints REST reactivos (GET /me, PATCH /me) para consulta
 *   y edición del perfil de usuario autenticado.
 * Regla de Dependencia: Puede importar anotaciones de Spring WebFlux, DTOs de
 *   perfil y el puerto de entrada ProfileUseCasePort. Tiene prohibido invocar
 *   directamente a ProfilePostgrestAdapter sin pasar por Application.
 */
package ec.edu.ups.social.infrastructure.adapter.in.web;

import ec.edu.ups.social.domain.port.in.ProfileUseCasePort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/social/profile")
public class ProfileController {

    private final ProfileUseCasePort profileUseCasePort;

    public ProfileController(ProfileUseCasePort profileUseCasePort) {
        this.profileUseCasePort = profileUseCasePort;
    }

    @GetMapping("/me")
    public void getMyProfile() {
        // Esqueleto GET /api/v1/social/profile/me
    }

    @PatchMapping("/me")
    public void updateMyProfile() {
        // Esqueleto PATCH /api/v1/social/profile/me
    }
}
