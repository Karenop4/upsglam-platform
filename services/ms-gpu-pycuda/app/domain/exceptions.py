"""
Capa: Domain
Responsabilidad: Excepciones de dominio (GpuUnavailableError,
  InvalidImageFormatError, QueueCapacityExceededError).
Regla de Dependencia: CERO dependencias de FastAPI (HTTPException), PyCUDA o
  frameworks externos. Son lanzadas por Application/Infrastructure y traducidas
  a códigos HTTP (400, 429, 503) únicamente en la capa de Infrastructure
  (routes.py).
"""


class DomainError(Exception):
    """Clase base para errores de dominio del servicio GPU."""


class GpuUnavailableError(DomainError):
    """Se lanza cuando no existe dispositivo NVIDIA/contexto CUDA operativo."""


class InvalidImageFormatError(DomainError):
    """Se lanza cuando la cabecera real o dimensiones de la imagen son inválidas."""


class QueueCapacityExceededError(DomainError):
    """Se lanza cuando la cola o semáforo de concurrencia de la GPU está llena."""
