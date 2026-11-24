/**
 * Capa: Infrastructure (Inbound Web Adapter)
 * Responsabilidad: Endpoints REST reactivos (Mono/Flux) para feed y CRUD
 *   completo de publicaciones propias (GET, POST, PATCH /{postId},
 *   DELETE /{postId}).
 * Regla de Dependencia: Puede importar anotaciones de Spring WebFlux, Project
 *   Reactor (Mono/Flux), DTOs (PostRequestDto, PostResponseDto) y el puerto de
 *   entrada PostUseCasePort. Tiene prohibido invocar directamente a
 *   PostRepositoryPort o PostPostgrestAdapter saltándose la capa Application.
 */
package ec.edu.ups.social.infrastructure.adapter.in.web;

import ec.edu.ups.social.domain.port.in.PostUseCasePort;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/social/posts")
public class PostController {

    private final PostUseCasePort postUseCasePort;

    public PostController(PostUseCasePort postUseCasePort) {
        this.postUseCasePort = postUseCasePort;
    }

    @GetMapping
    public void getFeed() {
        // Esqueleto GET /api/v1/social/posts
    }

    @PostMapping
    public void createPost() {
        // Esqueleto POST /api/v1/social/posts
    }

    @PatchMapping("/{postId}")
    public void updatePost(@PathVariable UUID postId) {
        // Esqueleto PATCH /api/v1/social/posts/{postId} validando propietario
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable UUID postId) {
        // Esqueleto DELETE /api/v1/social/posts/{postId} validando propietario
    }
}
