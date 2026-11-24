/**
 * Capa: Infrastructure (API Gateway CORS Configuration)
 * Responsabilidad: Configuración centralizada de CORS para clientes móviles y
 *   pruebas de carga.
 * Regla de Dependencia: Utiliza únicamente abstracciones reactivas de Spring
 *   WebFlux (CorsWebFilter) en el borde del API Gateway. Prohibido duplicar
 *   políticas CORS contradictorias en los microservicios internos.
 */
package ec.edu.ups.gateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig {
    // Esqueleto de configuración reactiva de CorsWebFilter
}
