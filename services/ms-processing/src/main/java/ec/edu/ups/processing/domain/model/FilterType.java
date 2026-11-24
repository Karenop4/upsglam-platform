/**
 * Capa: Domain
 * Responsabilidad: Enum con los 6 filtros permitidos (MEDIA, NITIDEZ,
 *   LAPLACIANO, UPS_IDENTITY, EXTRA_1, EXTRA_2).
 * Regla de Dependencia: CERO dependencias externas. Vocabulario ubicuo del
 *   dominio compartido entre ProcessingJob, casos de uso y adaptadores.
 */
package ec.edu.ups.processing.domain.model;

public enum FilterType {
    MEDIA,
    NITIDEZ,
    LAPLACIANO,
    UPS_IDENTITY,
    EXTRA_1,
    EXTRA_2
}
