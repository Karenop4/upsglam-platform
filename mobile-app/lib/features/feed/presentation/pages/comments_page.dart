/**
 * Capa: Presentation (Feature: feed)
 * Responsabilidad: Vista modal / página de comentarios de una publicación con
 *   envío de nuevos comentarios y recepción instantánea en tiempo real.
 * Regla de Dependencia: Puede importar Flutter Material y entidades de Domain
 *   del feature feed. Prohibido llamar directamente a DioClient o
 *   SocialRealtimeDatasource desde el widget.
 */
import 'package:flutter/material.dart';

class CommentsPage extends StatelessWidget {
  const CommentsPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('UPSGlam - Comentarios en Tiempo Real'),
      ),
    );
  }
}
