/**
 * Capa: Presentation (Feature: studio)
 * Responsabilidad: UI de carga de imagen, selector de filtros/parámetros,
 *   prevención de doble envío accidental, vista comparativa (original vs
 *   resultado), descarga y botón de publicar.
 * Regla de Dependencia: Puede importar Flutter Material y entidades de Domain
 *   (FilterOption). Tiene prohibido comunicarse directamente con DioClient o
 *   ProcessingRemoteDatasource sin pasar por el gestor de estado/caso de uso.
 */
import 'package:flutter/material.dart';

class StudioPage extends StatelessWidget {
  const StudioPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('UPSGlam - Estudio de Procesamiento GPU CUDA'),
      ),
    );
  }
}
