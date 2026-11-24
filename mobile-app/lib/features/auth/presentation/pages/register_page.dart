/**
 * Capa: Presentation (Feature: auth)
 * Responsabilidad: UI dedicada para el registro de nuevos usuarios (email,
 *   contraseña, username) vinculada a Supabase Auth y creación de perfil.
 * Regla de Dependencia: Puede importar Flutter Material y comunicarse con el
 *   contrato AuthRepository (capa Domain). Prohibido instanciar directamente
 *   SupabaseAuthRepositoryImpl o clientes HTTP.
 */
import 'package:flutter/material.dart';

class RegisterPage extends StatelessWidget {
  const RegisterPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('UPSGlam - Registro de Nuevo Usuario'),
      ),
    );
  }
}
