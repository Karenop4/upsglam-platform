/**
 * Capa: Domain
 * Responsabilidad: Entidad que representa una solicitud de procesamiento (ID,
 *   usuario, filtro, estado, URLs original/procesada).
 * Regla de Dependencia: CERO dependencias de Spring, R2DBC, WebClient o
 *   frameworks externos. Solo puede referenciar tipos estándar de Java y otros
 *   objetos de domain.model (FilterType, GpuMetrics).
 */
package ec.edu.ups.processing.domain.model;

import java.time.Instant;
import java.util.UUID;

public record ProcessingJob(
        UUID id,
        UUID userId,
        FilterType filterType,
        String status,
        String originalImageUrl,
        String processedImageUrl,
        GpuMetrics gpuMetrics,
        Instant createdAt
) {
}
