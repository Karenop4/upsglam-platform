/**
 * Capa: Testing (API Gateway Integration Tests)
 * Responsabilidad: Verificar de extremo a extremo la seguridad y el
 *   enrutamiento del Gateway: validación JWT (ES256 vía JWKS y HS256 legado),
 *   claims iss/aud/exp, anti-suplantación de X-User-Id, recorte de /api/v1,
 *   rutas denegadas y fallback 503 cuando un microservicio está caído.
 * Regla de Dependencia: Usa un servidor HTTP del JDK como JWKS falso y como
 *   microservicio de eco; no depende de Supabase ni de Docker.
 */
package ec.edu.ups.gateway;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayApplicationTests {

    private static final String ISSUER = "https://test-project.supabase.co/auth/v1";
    private static final String LEGACY_SECRET = "test-legacy-secret-with-at-least-32-bytes!!";
    private static final ECKey SIGNING_KEY;
    private static final HttpServer FAKE_UPSTREAM;
    private static final int CLOSED_PORT;

    static {
        try {
            SIGNING_KEY = new ECKeyGenerator(Curve.P_256).keyID("test-kid").generate();
            FAKE_UPSTREAM = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            String jwks = new JWKSet(SIGNING_KEY.toPublicJWK()).toString();
            FAKE_UPSTREAM.createContext("/jwks", ex -> respond(ex, jwks));
            FAKE_UPSTREAM.createContext("/", ex -> {
                String userId = ex.getRequestHeaders().getFirst("X-User-Id");
                boolean hasAuth = ex.getRequestHeaders().containsKey(HttpHeaders.AUTHORIZATION);
                respond(ex, "{\"path\":\"%s\",\"userId\":\"%s\",\"authForwarded\":%s}"
                        .formatted(ex.getRequestURI().getPath(), userId, hasAuth));
            });
            FAKE_UPSTREAM.start();
            try (ServerSocket s = new ServerSocket(0)) {
                CLOSED_PORT = s.getLocalPort();
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        String upstream = "http://127.0.0.1:" + FAKE_UPSTREAM.getAddress().getPort();
        registry.add("SUPABASE_JWKS_URL", () -> upstream + "/jwks");
        registry.add("SUPABASE_URL", () -> "https://test-project.supabase.co");
        registry.add("SUPABASE_JWT_SECRET", () -> LEGACY_SECRET);
        registry.add("MS_SOCIAL_URL", () -> upstream);
        registry.add("MS_PROCESSING_URL", () -> "http://127.0.0.1:" + CLOSED_PORT);
    }

    @AfterAll
    static void stopUpstream() {
        FAKE_UPSTREAM.stop(0);
    }

    @Autowired
    private Environment environment;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + environment.getProperty("local.server.port"))
                .build();
    }

    // ---------------------------------------------------------------- health

    @Test
    void healthIsPublic() {
        client.get().uri("/actuator/health").exchange().expectStatus().isOk();
        client.get().uri("/actuator/health/readiness").exchange().expectStatus().isOk();
    }

    @Test
    void otherActuatorEndpointsAreNotExposed() {
        client.get().uri("/actuator/env").header(HttpHeaders.AUTHORIZATION, bearer(validEs256(user())))
                .exchange().expectStatus().isForbidden();
    }

    // ---------------------------------------------------------- autenticación

    @Test
    void missingTokenIsRejectedWithJson401() {
        client.get().uri("/api/v1/social/posts").exchange()
                .expectStatus().isUnauthorized()
                .expectHeader().valueEquals(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
                .expectBody().jsonPath("$.status").isEqualTo(401);
    }

    @Test
    void validEs256TokenIsRoutedWithPrefixStrippedAndIdentityInjected() {
        String sub = user();
        client.get().uri("/api/v1/social/posts")
                .header(HttpHeaders.AUTHORIZATION, bearer(validEs256(sub)))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/social/posts")
                .jsonPath("$.userId").isEqualTo(sub)
                .jsonPath("$.authForwarded").isEqualTo(true);
    }

    @Test
    void spoofedUserIdHeaderIsOverwrittenWithTokenSubject() {
        String sub = user();
        client.get().uri("/api/v1/social/profile/me")
                .header(HttpHeaders.AUTHORIZATION, bearer(validEs256(sub)))
                .header("X-User-Id", "00000000-0000-0000-0000-000000000000")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.userId").isEqualTo(sub);
    }

    @Test
    void expiredTokenIsRejected() throws Exception {
        JWTClaimsSet claims = baseClaims(user())
                .expirationTime(Date.from(Instant.now().minusSeconds(300))).build();
        assertUnauthorized(signEs256(claims));
    }

    @Test
    void wrongIssuerIsRejected() throws Exception {
        JWTClaimsSet claims = baseClaims(user()).issuer("https://evil.example.com/auth/v1").build();
        assertUnauthorized(signEs256(claims));
    }

    @Test
    void wrongAudienceIsRejected() throws Exception {
        JWTClaimsSet claims = baseClaims(user()).audience("anon").build();
        assertUnauthorized(signEs256(claims));
    }

    @Test
    void tokenSignedWithUnknownKeyIsRejected() throws Exception {
        ECKey attackerKey = new ECKeyGenerator(Curve.P_256).keyID("test-kid").generate();
        SignedJWT jwt = new SignedJWT(esHeader(), baseClaims(user()).build());
        jwt.sign(new ECDSASigner(attackerKey));
        assertUnauthorized(jwt.serialize());
    }

    @Test
    void garbageTokenIsRejected() {
        assertUnauthorized("not-a-jwt");
    }

    // ------------------------------------------------------------ HS256 legado

    @Test
    void legacyHs256TokenWithCorrectSecretIsAccepted() throws Exception {
        String sub = user();
        client.get().uri("/api/v1/social/posts")
                .header(HttpHeaders.AUTHORIZATION, bearer(signHs256(baseClaims(sub).build(), LEGACY_SECRET)))
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.userId").isEqualTo(sub);
    }

    @Test
    void hs256TokenWithWrongSecretIsRejected() throws Exception {
        assertUnauthorized(signHs256(baseClaims(user()).build(), "another-secret-that-is-also-32-bytes-long"));
    }

    // ------------------------------------------------------- rutas y fallback

    @Test
    void internalRoutesAreNotReachable() {
        client.get().uri("/internal/gpu/health")
                .header(HttpHeaders.AUTHORIZATION, bearer(validEs256(user())))
                .exchange().expectStatus().isForbidden();
    }

    @Test
    void downstreamDownReturns503InsteadOf401() {
        client.get().uri("/api/v1/processing/filters")
                .header(HttpHeaders.AUTHORIZATION, bearer(validEs256(user())))
                .exchange()
                .expectStatus().isEqualTo(503)
                .expectBody().jsonPath("$.status").isEqualTo(503);
    }

    // ---------------------------------------------------------------- helpers

    private void assertUnauthorized(String token) {
        client.get().uri("/api/v1/social/posts")
                .header(HttpHeaders.AUTHORIZATION, bearer(token))
                .exchange().expectStatus().isUnauthorized();
    }

    private static String user() {
        return UUID.randomUUID().toString();
    }

    private static String bearer(String token) {
        return "Bearer " + token;
    }

    private static JWTClaimsSet.Builder baseClaims(String sub) {
        Instant now = Instant.now();
        return new JWTClaimsSet.Builder()
                .subject(sub)
                .issuer(ISSUER)
                .audience("authenticated")
                .claim("role", "authenticated")
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(3600)));
    }

    private static JWSHeader esHeader() {
        return new JWSHeader.Builder(JWSAlgorithm.ES256).keyID("test-kid").type(JOSEObjectType.JWT).build();
    }

    private static String validEs256(String sub) {
        try {
            return signEs256(baseClaims(sub).build());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String signEs256(JWTClaimsSet claims) throws Exception {
        SignedJWT jwt = new SignedJWT(esHeader(), claims);
        jwt.sign(new ECDSASigner(SIGNING_KEY));
        return jwt.serialize();
    }

    private static String signHs256(JWTClaimsSet claims, String secret) throws Exception {
        SignedJWT jwt = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build(), claims);
        jwt.sign(new MACSigner(secret.getBytes(StandardCharsets.UTF_8)));
        return jwt.serialize();
    }

    private static void respond(HttpExchange ex, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json");
        ex.sendResponseHeaders(200, bytes.length);
        ex.getResponseBody().write(bytes);
        ex.close();
    }
}
