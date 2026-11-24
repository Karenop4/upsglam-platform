/**
 * Capa: Infrastructure (Inbound Web Adapter)
 * Responsabilidad: Endpoints REST reactivos para consultar el historial de
 *   solicitudes de procesamiento (processing_requests) y métricas GPU
 *   (gpu_metrics) del usuario autenticado.
 * Regla de Dependencia: Puede importar Spring WebFlux, Reactor (Flux/Mono) y
 *   el puerto de entrada ProcessImageUseCasePort. Prohibido comunicarse
 *   directamente con JobPostgrestAdapter saltándose el caso de uso.
 */
package ec.edu.ups.processing.infrastructure.adapter.in.web;

import ec.edu.ups.processing.domain.port.in.ProcessImageUseCasePort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/processing/history")
public class HistoryController {

    private final ProcessImageUseCasePort processImageUseCasePort;

    public HistoryController(ProcessImageUseCasePort processImageUseCasePort) {
        this.processImageUseCasePort = processImageUseCasePort;
    }

    @GetMapping
    public void getUserProcessingHistory() {
        // Esqueleto GET /api/v1/processing/history
    }
}
