"""
Capa: Infrastructure (Inbound HTTP API Adapter)
Responsabilidad: Endpoints FastAPI (/process, /health y /health/gpu) con
  validación estricta de formato real, tamaño y parámetros, más consulta de
  resultados temporales.
Regla de Dependencia: Puede importar FastAPI, esquemas Pydantic, el caso de uso
  ImageProcessorUseCase, CudaContextManager y las excepciones de Domain para
  mapearlas a códigos HTTP (400, 429, 503). Tiene prohibido invocar kernels CUDA
  directamente sin pasar por ImageProcessorUseCase.
"""
from fastapi import APIRouter

router = APIRouter()


@router.get("/health")
async def health_check() -> dict:
    """Esqueleto de endpoint /health que verifica disponibilidad de servicio y GPU CUDA."""
    return {"status": "UP", "gpu_required": True}


@router.get("/health/gpu")
async def gpu_detailed_health_check() -> dict:
    """Esqueleto de endpoint /health/gpu que diagnostica contexto CUDA y VRAM."""
    return {"status": "UP", "cuda_context": "INITIALIZED"}


@router.post("/process")
async def process_image_on_gpu() -> dict:
    """Esqueleto de endpoint /process para ejecución de los 6 filtros CUDA."""
    return {"status": "COMPLETED"}
