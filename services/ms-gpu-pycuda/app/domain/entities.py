"""
Capa: Domain
Responsabilidad: Dataclasses para ImageTask, FilterParameters, GpuMetrics y
  estados de tarea.
Regla de Dependencia: CERO dependencias de FastAPI, PyCUDA, NumPy o librerías de
  I/O externas. Solo puede importar módulos de la librería estándar de Python
  (dataclasses, enum, typing, uuid).
"""
from dataclasses import dataclass
from enum import Enum
from typing import Tuple


class TaskStatus(str, Enum):
    QUEUED = "QUEUED"
    PROCESSING = "PROCESSING"
    COMPLETED = "COMPLETED"
    FAILED = "FAILED"


@dataclass(frozen=True)
class FilterParameters:
    filter_type: str
    kernel_size: int = 3
    intensity: float = 1.0


@dataclass(frozen=True)
class GpuMetrics:
    width: int
    height: int
    block_dim: Tuple[int, int, int]
    grid_dim: Tuple[int, int, int]
    total_threads: int
    kernel_time_ms: float


@dataclass
class ImageTask:
    request_id: str
    parameters: FilterParameters
    status: TaskStatus = TaskStatus.QUEUED
