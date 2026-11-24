/**
 * Capa: Core (Domain Error Hierarchy)
 * Responsabilidad: Jerarquía de fallos de dominio para mostrar mensajes claros
 *   al usuario.
 * Regla de Dependencia: CERO dependencias de paquetes HTTP (Dio), Supabase SDK
 *   o Flutter Widgets (material.dart). Puede ser importado por las capas
 *   Domain, Data y Presentation de todos los features.
 */
abstract class Failure {
  final String message;
  const Failure(this.message);
}

class NetworkFailure extends Failure {
  const NetworkFailure(super.message);
}

class GpuUnavailableFailure extends Failure {
  const GpuUnavailableFailure(super.message);
}

class QueueFullFailure extends Failure {
  const QueueFullFailure(super.message);
}
