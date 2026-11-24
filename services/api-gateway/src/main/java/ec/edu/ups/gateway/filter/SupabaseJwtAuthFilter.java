/**
 * Capa: Infrastructure (API Gateway Security Filter - Asymmetric JWKS & HMAC Fallback)
 * Responsabilidad: Filtro global reactivo que valida el token JWT emitido por
 *   Supabase Auth soportando verificación de firma asimétrica (ES256/RS256)
 *   mediante el endpoint público JWKS (.well-known/jwks.json de Supabase) con
 *   caché en memoria y fallback controlado a HMAC-SHA256 (SUPABASE_JWT_SECRET),
 *   inyectando X-User-Id y propagando el header Authorization hacia los
 *   microservicios internos para que PostgREST aplique Row Level Security (RLS).
 * Regla de Dependencia: Puede comunicarse con componentes de Spring Cloud
 *   Gateway (GlobalFilter, ServerWebExchange), WebClient reactivo para obtener
 *   las claves públicas JWKS y utilidades criptográficas JWK/JWT. Tiene
 *   prohibido realizar llamadas bloqueantes o acoplarse a la lógica de negocio
 *   de ms-social o ms-processing.
 */
package ec.edu.ups.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SupabaseJwtAuthFilter {

    @Value("${SUPABASE_JWKS_URL:https://your-project-ref.supabase.co/auth/v1/.well-known/jwks.json}")
    private String supabaseJwksUrl;

    @Value("${SUPABASE_JWT_SECRET:}")
    private String supabaseJwtSecretFallback;

    // Esqueleto de filtro reactivo con soporte dual:
    // 1. Verificación primaria mediante claves públicas asimétricas (JWKS: ES256/RS256)
    // 2. Fallback opcional a secreto simétrico (HS256) si el header 'alg' del token es HS256
    // 3. Inyección de X-User-Id (claim 'sub') y preservación de Authorization Bearer para RLS en PostgREST
}
