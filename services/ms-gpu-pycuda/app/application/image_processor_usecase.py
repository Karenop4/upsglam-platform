"""
Capa: Application (Use Case)
Responsabilidad: Caso de uso principal que valida entradas, coordina el
  semáforo/cola de concurrencia, invoca el kernel correspondiente y empaqueta
  métricas.
Regla de Dependencia: Puede comunicarse con la capa Domain (entities.py,
  exceptions.py), con ConcurrencyManager y con abstracciones inyectadas de
  ejecución de kernels e I/O de imagen. Tiene prohibido importar objetos HTTP
  de FastAPI (Request, Response, HTTPException).
"""
from app.application.concurrency_manager import ConcurrencyManager


class ImageProcessorUseCase:
    """Esqueleto del caso de uso orquestador de procesamiento paralelo en GPU."""

    def __init__(self, concurrency_manager: ConcurrencyManager) -> None:
        self._concurrency_manager = concurrency_manager
