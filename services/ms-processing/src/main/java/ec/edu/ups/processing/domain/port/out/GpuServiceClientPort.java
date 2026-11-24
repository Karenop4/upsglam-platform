/**
 * Capa: Domain (Outbound Port)
 * Responsabilidad: Puerto de salida para invocar al microservicio Python PyCUDA.
 * Regla de Dependencia: CERO dependencias de Spring WebClient o HTTP. Es
 *   consumido por Application (ProcessImageUseCaseImpl) e implementado en
 *   Infrastructure (PyCudaWebClientAdapter).
 */
package ec.edu.ups.processing.domain.port.out;

import ec.edu.ups.processing.domain.model.FilterType;
import ec.edu.ups.processing.domain.model.GpuMetrics;

public interface GpuServiceClientPort {
    // Esqueleto de contrato de salida para ejecución remota en ms-gpu-pycuda
}
