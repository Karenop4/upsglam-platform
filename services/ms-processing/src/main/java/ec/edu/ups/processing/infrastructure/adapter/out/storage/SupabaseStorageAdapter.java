/**
 * Capa: Infrastructure (Outbound Storage Adapter)
 * Responsabilidad: Cliente reactivo para subida de archivos y generación de
 *   URLs públicas/firmadas en Supabase Storage.
 * Regla de Dependencia: Implementa el puerto de dominio StoragePort mediante
 *   WebClient reactivo hacia la API REST de Supabase Storage. Prohibido
 *   acoplar detalles del protocolo de Supabase en la capa Domain o Application.
 */
package ec.edu.ups.processing.infrastructure.adapter.out.storage;

import ec.edu.ups.processing.domain.port.out.StoragePort;
import org.springframework.stereotype.Component;

@Component
public class SupabaseStorageAdapter implements StoragePort {
    // Esqueleto de cliente reactivo hacia Supabase Storage
}
