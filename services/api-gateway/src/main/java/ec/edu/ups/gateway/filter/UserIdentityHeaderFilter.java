/**
 * Capa: Infrastructure (API Gateway Identity Propagation Filter)
 * Responsabilidad: Propagar la identidad autenticada a los microservicios:
 *   - SIEMPRE elimina cualquier X-User-Id enviado por el cliente (anti
 *     suplantación).
 *   - Si el JWT fue validado por Spring Security, inyecta X-User-Id con el
 *     claim 'sub'.
 *   - El header Authorization se reenvía intacto para que PostgREST aplique
 *     Row Level Security (RLS) con el JWT del usuario.
 * Regla de Dependencia: Depende de Spring Cloud Gateway (GlobalFilter) y del
 *   JwtAuthenticationToken de Spring Security. Prohibido validar tokens aquí:
 *   esa responsabilidad es exclusiva de SecurityConfig.
 */
package ec.edu.ups.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UserIdentityHeaderFilter implements GlobalFilter, Ordered {

    public static final String USER_ID_HEADER = "X-User-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getPrincipal()
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(principal -> ((JwtAuthenticationToken) principal).getToken().getSubject())
                .map(userId -> exchange.mutate()
                        .request(r -> r.headers(h -> h.set(USER_ID_HEADER, userId)))
                        .build())
                .defaultIfEmpty(exchange.mutate()
                        .request(r -> r.headers(h -> h.remove(USER_ID_HEADER)))
                        .build())
                .flatMap(chain::filter);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
