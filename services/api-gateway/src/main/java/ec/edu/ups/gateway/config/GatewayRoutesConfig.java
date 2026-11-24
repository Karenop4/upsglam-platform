/**
 * Capa: Infrastructure (API Gateway Routing Configuration)
 * Responsabilidad: Define las rutas reactivas hacia ms-social y ms-processing
 *   usando los nombres de servicio de Docker Compose.
 * Regla de Dependencia: Puede importar clases de configuración de Spring Cloud
 *   Gateway y filtros locales del Gateway (SupabaseJwtAuthFilter). Tiene
 *   prohibido acoplarse a clases internas de ms-social o ms-processing o usar
 *   localhost harcodeado para comunicación entre contenedores.
 */
package ec.edu.ups.gateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {
    // Esqueleto de definición de RouteLocator reactivo hacia http://ms-social:8081 y http://ms-processing:8082
}
