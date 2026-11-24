/**
 * Capa: Infrastructure (Outbound HTTP Adapter)
 * Responsabilidad: Cliente WebClient reactivo hacia http://ms-gpu-pycuda:8000
 *   con manejo de timeouts y propagación de request_id.
 * Regla de Dependencia: Implementa el puerto de salida GpuServiceClientPort
 *   utilizando Spring WebClient reactivo. Tiene prohibido usar localhost para
 *   referenciar al contenedor GPU o simular métricas cuando el servicio falla.
 */
package ec.edu.ups.processing.infrastructure.adapter.out.gpu;

import ec.edu.ups.processing.domain.port.out.GpuServiceClientPort;
import org.springframework.stereotype.Component;

@Component
public class PyCudaWebClientAdapter implements GpuServiceClientPort {
    // Esqueleto de cliente reactivo WebClient hacia http://ms-gpu-pycuda:8000
}
