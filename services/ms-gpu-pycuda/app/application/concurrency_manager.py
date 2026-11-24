"""
Capa: Application (Concurrency Control Policy)
Responsabilidad: Política explícita de atención a solicitudes simultáneas (cola
  acotada o rechazo controlado HTTP 429) documentando el comportamiento real.
Regla de Dependencia: Solo depende de la librería estándar (asyncio) y de
  excepciones de Domain (QueueCapacityExceededError). Tiene prohibido acoplarse
  a FastAPI o directamente a llamadas de bajo nivel de PyCUDA.
"""


class ConcurrencyManager:
    """Esqueleto de gestor de concurrencia con cola acotada para proteger VRAM de la GPU."""

    def __init__(self, max_concurrent_tasks: int, max_queue_size: int) -> None:
        self.max_concurrent_tasks = max_concurrent_tasks
        self.max_queue_size = max_queue_size
