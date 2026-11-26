/**
 * Capa: Infrastructure (Inbound Web Adapter)
 * Responsabilidad: Endpoints REST reactivos para envío de imagen/parámetros
 *   (POST /jobs) y listado del catálogo de los 6 filtros CUDA disponibles
 *   (GET /filters) con validación estricta de entrada y política de rechazo
 *   controlado HTTP 429 cuando la cola de GPU está llena.
 * Regla de Dependencia: Puede importar Spring WebFlux, Jakarta Validation y el
 *   puerto de entrada ProcessImageUseCasePort. Tiene prohibido comunicarse
 *   directamente con PyCudaWebClientAdapter, SupabaseStorageAdapter o
 *   JobPostgrestAdapter sin pasar por el caso de uso.
 */
package ec.edu.ups.processing.infrastructure.adapter.in.web;

import ec.edu.ups.processing.domain.port.in.ProcessImageUseCasePort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processing")
public class ProcessingController {

    private final ProcessImageUseCasePort processImageUseCasePort;

    public ProcessingController(ProcessImageUseCasePort processImageUseCasePort) {
        this.processImageUseCasePort = processImageUseCasePort;
    }

    @GetMapping("/filters")
    public void listAvailableFilters() {
        // Esqueleto GET /api/v1/processing/filters (6 filtros CUDA permitidos)
    }

    @PostMapping("/jobs")
    public void submitProcessingJob() {
        // Esqueleto POST /api/v1/processing/jobs con validación de entrada
    }
}
