/**
 * Capa: Infrastructure (Outbound Persistence Adapter - Supabase PostgREST)
 * Responsabilidad: Adaptador reactivo que implementa ProfileRepositoryPort
 *   contra PostgREST (/rest/v1/profiles) respetando Row Level Security (RLS).
 * Regla de Dependencia: Implementa ProfileRepositoryPort mediante WebClient
 *   reactivo. Prohibido ser invocado directamente desde ProfileController.
 */
package ec.edu.ups.social.infrastructure.adapter.out.persistence;

import ec.edu.ups.social.domain.port.out.ProfileRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class ProfilePostgrestAdapter implements ProfileRepositoryPort {
    // Esqueleto de cliente WebClient reactivo hacia SUPABASE_REST_URL/profiles
}
