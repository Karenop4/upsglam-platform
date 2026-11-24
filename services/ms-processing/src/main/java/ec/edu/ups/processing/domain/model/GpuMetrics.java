/**
 * Capa: Domain (Value Object)
 * Responsabilidad: Value Object inmutable con métricas GPU (ancho, alto,
 *   bloque, grid, total de hilos, tiempo de kernel en ms).
 * Regla de Dependencia: CERO dependencias de Spring o librerías externas.
 *   Es instanciado únicamente con métricas reales medidas por eventos CUDA
 *   recibidas a través de GpuServiceClientPort; prohibido generar valores
 *   simulados o por defecto.
 */
package ec.edu.ups.processing.domain.model;

public record GpuMetrics(
        int width,
        int height,
        String blockDim,
        String gridDim,
        long totalThreads,
        double kernelTimeMs
) {
}
