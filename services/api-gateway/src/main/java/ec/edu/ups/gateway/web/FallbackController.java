/**
 * Capa: Infrastructure (API Gateway Circuit Breaker Fallback)
 * Responsabilidad: Responder de forma controlada cuando un microservicio
 *   interno no está disponible (503) o excede su timeout (504), en lugar de
 *   propagar errores genéricos o 401 engañosos al cliente móvil.
 * Regla de Dependencia: Solo es invocado internamente por el filtro
 *   CircuitBreaker (forward:/fallback/**); el acceso externo directo a
 *   /fallback/** está denegado por SecurityConfig.
 */
package ec.edu.ups.gateway.web;

import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    private static final Logger log = LoggerFactory.getLogger(FallbackController.class);

    private final JsonErrorWriter errorWriter;

    public FallbackController(JsonErrorWriter errorWriter) {
        this.errorWriter = errorWriter;
    }

    @RequestMapping("/fallback/{service}")
    public Mono<Void> fallback(@PathVariable String service, ServerWebExchange exchange) {
        Throwable cause = exchange.getAttribute(ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR);
        boolean timeout = cause instanceof TimeoutException
                || (cause != null && cause.getCause() instanceof TimeoutException);
        HttpStatus status = timeout ? HttpStatus.GATEWAY_TIMEOUT : HttpStatus.SERVICE_UNAVAILABLE;

        log.error("Fallback para '{}' ({}): {}", service, status.value(),
                cause != null ? cause.toString() : "circuito abierto");
        String message = timeout
                ? "El servicio '%s' no respondió a tiempo".formatted(service)
                : "El servicio '%s' no está disponible temporalmente".formatted(service);
        return errorWriter.write(exchange, status, message);
    }
}
