# syntax=docker/dockerfile:1.7
# ==============================================================================
# Capa: Infrastructure (Container Build Definition - api-gateway)
# Responsabilidad: Construir la imagen de api-gateway en dos etapas:
#   1. build: compila con Maven Wrapper sobre JDK 21 (caché de ~/.m2 con BuildKit).
#   2. runtime: solo JRE 21 + el jar, ejecutado con usuario sin privilegios.
# Regla de Dependencia: Empaqueta únicamente este servicio. Los tests se ejecutan
#   en el pipeline (./mvnw verify), no durante el build de la imagen.
# ==============================================================================

FROM eclipse-temurin:21-jdk-noble AS build
WORKDIR /build

COPY mvnw pom.xml ./
COPY .mvn/ .mvn/
RUN --mount=type=cache,target=/root/.m2 \
    chmod +x mvnw && ./mvnw -B -q dependency:go-offline

COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -q -DskipTests package \
    && cp target/*-SNAPSHOT.jar /build/app.jar

FROM eclipse-temurin:21-jre-noble AS runtime

RUN groupadd --system --gid 10001 app \
    && useradd --system --uid 10001 --gid app --no-create-home --shell /usr/sbin/nologin app

WORKDIR /app
COPY --from=build --chown=app:app /build/app.jar app.jar

USER app:app
EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
