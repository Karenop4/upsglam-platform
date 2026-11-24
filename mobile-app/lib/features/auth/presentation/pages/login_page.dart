/**
 * Capa: Presentation (Feature: auth)
 * Responsabilidad: UI de inicio de sesión y registro.
 * Regla de Dependencia: Puede importar Flutter Material y comunicarse con la
 *   abstracción AuthRepository (o su gestor de estado). Tiene prohibido
 *   instanciar directamente SupabaseAuthRepositoryImpl o clientes HTTP de bajo
 *   nivel.
 */
import 'package:flutter/material.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('UPSGlam - Login & Registro'),
      ),
    );
  }
}
