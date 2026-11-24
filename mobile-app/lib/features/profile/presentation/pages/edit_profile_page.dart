/**
 * Capa: Presentation (Feature: profile)
 * Responsabilidad: Pantalla de edición de perfil del usuario autenticado
 *   (username, biografía y avatar).
 * Regla de Dependencia: Puede importar Flutter Material y comunicarse con casos
 *   de uso o repositorios de perfil. Prohibido invocar directamente DioClient
 *   desde la capa Presentation.
 */
import 'package:flutter/material.dart';

class EditProfilePage extends StatelessWidget {
  const EditProfilePage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('UPSGlam - Edición de Perfil de Usuario'),
      ),
    );
  }
}
