/**
 * Capa: Presentation (Feature: profile)
 * Responsabilidad: Gestión básica de perfil e historial de imágenes procesadas
 *   con filtro, estado y métricas GPU.
 * Regla de Dependencia: Puede importar Flutter Material y entidades de dominio
 *   del usuario e historial de procesamiento. Prohibido invocar directamente
 *   adaptadores de red o base de datos desde la vista.
 */
import 'package:flutter/material.dart';

class ProfileHistoryPage extends StatelessWidget {
  const ProfileHistoryPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('UPSGlam - Perfil e Historial de Métricas GPU'),
      ),
    );
  }
}
