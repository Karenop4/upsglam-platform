"""
Capa: Infrastructure (CUDA Hardware Adapter)
Responsabilidad: Inicialización y gestión segura del contexto CUDA y memoria
  GPU; diagnostica claramente si falta GPU (prohibido fallback silencioso a CPU).
Regla de Dependencia: Puede importar pycuda.driver y lanzar GpuUnavailableError
  de la capa Domain si el hardware NVIDIA no está presente o falla el contexto.
  Prohibido simular ejecución en CPU con NumPy/OpenCV si falla CUDA.
"""


class CudaContextManager:
    """Esqueleto de administración explícita de contexto PyCUDA y diagnóstico de GPU."""
    pass
