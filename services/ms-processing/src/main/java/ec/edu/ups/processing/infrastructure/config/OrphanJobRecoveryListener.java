/**
 * Capa: Infrastructure (Startup Lifecycle / Fault Recovery Adapter)
 * Responsabilidad: Recuperación de trabajos PROCESSING o QUEUED huérfanos al
 *   arrancar el microservicio ms-processing, marcándolos como FAILED por
 *   reinicio inesperado del contenedor sin inventar métricas GPU.
 * Regla de Dependencia: Escucha el evento ApplicationReadyEvent de Spring Boot
 *   e invoca a ProcessImageUseCasePort / JobRepositoryPort para sanear estados
 *   inconsistentes al arranque.
 */
package ec.edu.ups.processing.infrastructure.config;

import ec.edu.ups.processing.domain.port.out.JobRepositoryPort;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrphanJobRecoveryListener {

    private final JobRepositoryPort jobRepositoryPort;

    public OrphanJobRecoveryListener(JobRepositoryPort jobRepositoryPort) {
        this.jobRepositoryPort = jobRepositoryPort;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void recoverOrphanProcessingJobsOnStartup() {
        // Esqueleto de recuperación de registros en estado PROCESSING/QUEUED huérfanos -> FAILED
    }
}
