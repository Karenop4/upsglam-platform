/**
 * Capa: Presentation (Feature: feed)
 * Responsabilidad: UI del feed social con actualización en tiempo real.
 * Regla de Dependencia: Puede importar Flutter Material y entidades de Domain
 *   (PostEntity). Tiene prohibido realizar peticiones HTTP directas con
 *   DioClient o SocialRemoteDatasource desde el árbol de widgets.
 */
import 'package:flutter/material.dart';

class FeedPage extends StatelessWidget {
  const FeedPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('UPSGlam - Feed Social en Tiempo Real'),
      ),
    );
  }
}
