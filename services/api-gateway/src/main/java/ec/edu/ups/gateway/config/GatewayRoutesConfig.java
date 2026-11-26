/**
 * Capa: Infrastructure (API Gateway Routing Configuration)
 * Responsabilidad: Rutas reactivas del borde hacia ms-social y ms-processing:
 *   - Recorta el prefijo de versión: /api/v1/social/** -> /social/** y
 *     /api/v1/processing/** -> /processing/** (la versión vive solo en el borde).
 *   - Limita el tamaño de la petición (RequestSize).
 *   - Timeout de respuesta por ruta (processing es mayor por la GPU).
 *   - Circuit breaker con fallback JSON 503/504.
 *   Las URLs llegan de DownstreamServicesProperties (DNS de Docker, nunca
 *   localhost entre contenedores).
 * Regla de Dependencia: Depende de Spring Cloud Gateway y Spring Cloud
 *   CircuitBreaker. Prohibido acoplarse a clases internas de los microservicios.
 */
package ec.edu.ups.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@Configuration
@EnableConfigurationProperties({DownstreamServicesProperties.class, SupabaseSecurityProperties.class})
public class GatewayRoutesConfig {

    static final String SOCIAL_CB = "socialCircuitBreaker";
    static final String PROCESSING_CB = "processingCircuitBreaker";

    /** Margen para que el timeout HTTP del cliente salte antes que el time limiter. */
    private static final Duration TIME_LIMITER_MARGIN = Duration.ofSeconds(2);

    @Bean
    public RouteLocator upsglamRoutes(RouteLocatorBuilder builder, DownstreamServicesProperties props) {
        return builder.routes()
                .route("ms-social", r -> r
                        .path("/api/v1/social/**")
                        .filters(f -> f
                                .rewritePath("/api/v1/(?<segment>.*)", "/${segment}")
                                .setRequestSize(props.maxRequestSize())
                                .circuitBreaker(cb -> cb
                                        .setName(SOCIAL_CB)
                                        .setFallbackUri("forward:/fallback/social")))
                        .metadata(RESPONSE_TIMEOUT_ATTR, props.social().responseTimeout().toMillis())
                        .uri(props.social().url()))
                .route("ms-processing", r -> r
                        .path("/api/v1/processing/**")
                        .filters(f -> f
                                .rewritePath("/api/v1/(?<segment>.*)", "/${segment}")
                                .setRequestSize(props.maxRequestSize())
                                .circuitBreaker(cb -> cb
                                        .setName(PROCESSING_CB)
                                        .setFallbackUri("forward:/fallback/processing")))
                        .metadata(RESPONSE_TIMEOUT_ATTR, props.processing().responseTimeout().toMillis())
                        .uri(props.processing().url()))
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerCustomizer(
            DownstreamServicesProperties props) {
        return factory -> {
            factory.configure(b -> b.circuitBreakerConfig(defaultCircuitBreaker())
                    .timeLimiterConfig(timeLimiter(props.social().responseTimeout())), SOCIAL_CB);
            factory.configure(b -> b.circuitBreakerConfig(defaultCircuitBreaker())
                    .timeLimiterConfig(timeLimiter(props.processing().responseTimeout())), PROCESSING_CB);
        };
    }

    private static CircuitBreakerConfig defaultCircuitBreaker() {
        return CircuitBreakerConfig.custom()
                .slidingWindowSize(20)
                .minimumNumberOfCalls(10)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(15))
                .permittedNumberOfCallsInHalfOpenState(3)
                .build();
    }

    private static TimeLimiterConfig timeLimiter(Duration responseTimeout) {
        return TimeLimiterConfig.custom().timeoutDuration(responseTimeout.plus(TIME_LIMITER_MARGIN)).build();
    }
}
