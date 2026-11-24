"""
Capa: Infrastructure (Temporary Result Store Adapter)
Responsabilidad: Almacenamiento temporal acotado (con TTL y limpieza automática)
  de las imágenes procesadas en GPU mientras ms-processing las transfiere a
  Supabase Storage, evitando fugas de memoria o disco en el contenedor GPU.
Regla de Dependencia: Es invocado desde la capa Application
  (ImageProcessorUseCase) o el endpoint de descarga interna en routes.py.
  Prohibido persistir imágenes de forma permanente fuera de Supabase Storage.
"""


class TempResultStore:
    """Esqueleto de almacén temporal con TTL para resultados de procesamiento GPU."""

    def __init__(self, ttl_seconds: int = 300) -> None:
        self.ttl_seconds = ttl_seconds
