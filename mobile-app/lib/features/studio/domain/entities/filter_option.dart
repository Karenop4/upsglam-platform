/**
 * Capa: Domain (Feature: studio)
 * Responsabilidad: Entidad de los 6 filtros disponibles y sus parámetros
 *   permitidos.
 * Regla de Dependencia: CERO dependencias de Flutter UI, Dio o serialización
 *   JSON de infraestructura. Define las reglas puras de los 6 filtros CUDA
 *   (MEDIA, NITIDEZ, LAPLACIANO, UPS_IDENTITY, EXTRA_1, EXTRA_2).
 */
enum GpuFilterType {
  media,
  nitidez,
  laplaciano,
  upsIdentity,
  extra1,
  extra2,
}

class FilterOption {
  final GpuFilterType type;
  final String displayName;
  final int kernelSize;
  final double intensity;

  const FilterOption({
    required this.type,
    required this.displayName,
    this.kernelSize = 3,
    this.intensity = 1.0,
  });
}
