/**
 * Capa: Infrastructure (Outbound Persistence Adapter - Supabase PostgREST)
 * Responsabilidad: Adaptador reactivo que implementa PostRepositoryPort
 *   consumiendo la API REST de Supabase (PostgREST /rest/v1/posts) mediante
 *   Spring WebClient y propagando el JWT del usuario para respetar las
 *   políticas nativas de Row Level Security (RLS).
 * Regla de Dependencia: Implementa el puerto de dominio PostRepositoryPort
 *   usando WebClient reactivo. Tiene prohibido ser invocado directamente por
 *   controladores web (PostController) o conectar directamente a PostgreSQL
 *   saltándose RLS.
 */
package ec.edu.ups.social.infrastructure.adapter.out.persistence;

import ec.edu.ups.social.domain.port.out.PostRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class PostPostgrestAdapter implements PostRepositoryPort {
    // Esqueleto de cliente WebClient reactivo hacia SUPABASE_REST_URL/posts con RLS
}
