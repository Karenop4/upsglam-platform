/**
 * Capa: Infrastructure (Outbound Persistence Adapter - Supabase PostgREST)
 * Responsabilidad: Persistencia reactiva de solicitudes de procesamiento
 *   (processing_requests), métricas reales de hardware CUDA (gpu_metrics) y
 *   catálogo de filtros (filters) contra la API REST de Supabase (PostgREST)
 *   usando Spring WebClient y respetando las políticas RLS.
 * Regla de Dependencia: Implementa el puerto de salida JobRepositoryPort
 *   utilizando WebClient reactivo hacia SUPABASE_REST_URL. Prohibido ser
 *   consumido directamente desde ProcessingController o HistoryController.
 */
package ec.edu.ups.processing.infrastructure.adapter.out.persistence;

import ec.edu.ups.processing.domain.port.out.JobRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class JobPostgrestAdapter implements JobRepositoryPort {
    // Esqueleto de persistencia reactiva vía PostgREST (/rest/v1/processing_requests y /rest/v1/gpu_metrics)
}
