/**
 * Capa: Domain (Outbound Port)
 * Responsabilidad: Puerto de salida para persistir historial y métricas GPU en
 *   PostgreSQL.
 * Regla de Dependencia: CERO dependencias de Spring Data R2DBC o SQL. Es
 *   utilizado por Application (ProcessImageUseCaseImpl) e implementado en
 *   Infrastructure (JobR2dbcAdapter).
 */
package ec.edu.ups.processing.domain.port.out;

import ec.edu.ups.processing.domain.model.ProcessingJob;

public interface JobRepositoryPort {
    // Esqueleto de contrato de salida para persistencia de ProcessingJob y GpuMetrics
}
