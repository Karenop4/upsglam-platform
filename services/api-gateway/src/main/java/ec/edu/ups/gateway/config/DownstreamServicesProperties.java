/**
 * Capa: Infrastructure (API Gateway Configuration Properties)
 * Responsabilidad: URLs y timeouts validados de los microservicios internos
 *   (ms-social y ms-processing) resueltos por DNS de la red de Docker.
 * Regla de Dependencia: Solo depende de Spring Boot y Jakarta Validation.
 */
package ec.edu.ups.gateway.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "upsglam.gateway")
public record DownstreamServicesProperties(
        @NotNull DataSize maxRequestSize,
        @Valid @NotNull Service social,
        @Valid @NotNull Service processing) {

    public record Service(
            @NotNull URI url,
            @NotNull Duration responseTimeout) {
    }
}
