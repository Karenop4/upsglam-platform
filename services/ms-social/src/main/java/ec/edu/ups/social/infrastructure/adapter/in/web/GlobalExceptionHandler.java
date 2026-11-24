/**
 * Capa: Infrastructure (Web Error Handling)
 * Responsabilidad: Manejo centralizado de errores HTTP con respuestas
 *   estandarizadas.
 * Regla de Dependencia: Captura excepciones provenientes de Domain, Application
 *   e Infrastructure en el borde HTTP de Spring WebFlux (@RestControllerAdvice)
 *   y las traduce a códigos de estado HTTP según openapi.yaml.
 */
package ec.edu.ups.social.infrastructure.adapter.in.web;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Esqueleto de traducción reactiva de excepciones a respuestas HTTP estandarizadas
}
