"""
Capa: Infrastructure (Image I/O Adapter)
Responsabilidad: Lectura, validación de cabecera real, conversión a arrays NumPy
  y escritura de imagen resultante (solo I/O, nunca aplica filtros).
Regla de Dependencia: Puede utilizar Pillow/NumPy para decodificar/codificar
  bytes y lanzar InvalidImageFormatError de la capa Domain si la cabecera binaria
  no corresponde a una imagen válida. Tiene estrictamente prohibido aplicar
  filtros o transformaciones de imagen en CPU.
"""


class ImageIOAdapter:
    """Esqueleto de adaptador de entrada/salida y validación de cabeceras de imagen."""
    pass
