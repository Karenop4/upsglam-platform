/**
 * Capa: Domain (Outbound Port)
 * Responsabilidad: Puerto de salida para subir/descargar imágenes en Supabase
 *   Storage.
 * Regla de Dependencia: CERO dependencias de SDKs de almacenamiento o clientes
 *   HTTP. Es invocado por Application (ProcessImageUseCaseImpl) e implementado
 *   por Infrastructure (SupabaseStorageAdapter).
 */
package ec.edu.ups.processing.domain.port.out;

public interface StoragePort {
    // Esqueleto de contrato de salida para almacenamiento de imágenes original y procesada
}
