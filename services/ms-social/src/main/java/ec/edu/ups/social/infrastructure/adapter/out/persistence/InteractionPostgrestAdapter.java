/**
 * Capa: Infrastructure (Outbound Persistence Adapter - Supabase PostgREST)
 * Responsabilidad: Adaptador reactivo que implementa InteractionRepositoryPort
 *   contra PostgREST (/rest/v1/likes y /rest/v1/comments) propagando el JWT del
 *   usuario para hacer cumplir RLS y disparar eventos en Supabase Realtime.
 * Regla de Dependencia: Implementa InteractionRepositoryPort mediante WebClient
 *   reactivo. Prohibido ser invocado directamente desde InteractionController.
 */
package ec.edu.ups.social.infrastructure.adapter.out.persistence;

import ec.edu.ups.social.domain.port.out.InteractionRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class InteractionPostgrestAdapter implements InteractionRepositoryPort {
    // Esqueleto de cliente WebClient reactivo hacia SUPABASE_REST_URL/likes y /comments
}
