/**
 * Capa: Presentation (Core Settings UI)
 * Responsabilidad: Pantalla de ajustes en la aplicación móvil para visualizar y
 *   modificar en caliente la URL base del API Gateway (ApiConfig) durante las
 *   pruebas en red local o remota en la defensa.
 * Regla de Dependencia: Puede importar Flutter Material y ApiConfig de la capa
 *   Core. Prohibido acoplar lógica de negocio de features específicos.
 */
import 'package:flutter/material.dart';

class ApiSettingsPage extends StatelessWidget {
  const ApiSettingsPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('UPSGlam - Configuración Dinámica de URL del API Gateway'),
      ),
    );
  }
}
