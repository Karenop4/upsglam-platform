/**
 * Capa: Domain (Inbound Port)
 * Responsabilidad: Puerto de entrada para solicitar procesamiento y consultar
 *   historial/estado.
 * Regla de Dependencia: CERO dependencias de Spring o frameworks externos.
 *   Es implementado por Application (ProcessImageUseCaseImpl) e invocado por
 *   Infrastructure (ProcessingController). Solo conoce modelos de domain.model.
 */
package ec.edu.ups.processing.domain.port.in;

import ec.edu.ups.processing.domain.model.FilterType;
import ec.edu.ups.processing.domain.model.ProcessingJob;

public interface ProcessImageUseCasePort {
    // Esqueleto de contrato de entrada para procesamiento de imagen y consulta de historial
}
