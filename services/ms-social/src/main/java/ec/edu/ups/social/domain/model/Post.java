/**
 * Capa: Domain
 * Responsabilidad: Entidad de dominio de una publicación con referencia a la
 *   solicitud de procesamiento (processingRequestId), imagen procesada y
 *   propietario.
 * Regla de Dependencia: CERO dependencias de Spring, WebClient, Jackson o
 *   cualquier framework externo. Solo puede comunicarse con objetos de
 *   domain.model y tipos estándar de Java.
 */
package ec.edu.ups.social.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Post(
        UUID id,
        UUID userId,
        UUID processingRequestId,
        String imageUrl,
        String caption,
        Instant createdAt
) {
}
