/**
 * Capa: Core (Configuration)
 * Responsabilidad: Gestión de URL base de la API configurable dinámicamente
 *   desde la app para pruebas en red local o remota.
 * Regla de Dependencia: Puede ser consumido por el cliente de red (DioClient)
 *   y pantallas de configuración. Tiene prohibido importar widgets de UI o
 *   depender de features específicos (auth, studio, feed, profile).
 */
class ApiConfig {
  String _baseUrl = 'http://10.0.2.2:8080/api/v1';

  String get baseUrl => _baseUrl;

  void updateBaseUrl(String newUrl) {
    _baseUrl = newUrl;
  }
}
