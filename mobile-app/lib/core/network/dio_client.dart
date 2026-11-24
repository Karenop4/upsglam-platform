/**
 * Capa: Core (Network Infrastructure)
 * Responsabilidad: Cliente HTTP con interceptores para adjuntar el token JWT de
 *   Supabase y manejar errores de red.
 * Regla de Dependencia: Depende de ApiConfig y es inyectado en los Remote
 *   Datasources de la capa Data de cada feature. Tiene prohibido ser importado
 *   desde las capas Domain o Presentation.
 */
import '../config/api_config.dart';

class DioClient {
  final ApiConfig apiConfig;

  DioClient({required this.apiConfig});
}
