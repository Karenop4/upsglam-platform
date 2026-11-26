"""
Capa: Infrastructure (Application Entrypoint & Dependency Injection)
Responsabilidad: Punto de entrada FastAPI, inyección de dependencias y ciclo de
  vida del contexto CUDA.
Regla de Dependencia: Ensambla los adaptadores de Infrastructure
  (CudaContextManager, ImageIOAdapter, routes) con los casos de uso de
  Application (ImageProcessorUseCase, ConcurrencyManager). Es el único punto de
  arranque del microservicio Python.
"""
from fastapi import FastAPI
from app.infrastructure.api.routes import router as api_router

app = FastAPI(title="UPSGlam GPU PyCUDA Service", version="1.0.0")
# Prefijo alineado con docs/architecture/openapi.yaml (/internal/gpu/*).
app.include_router(api_router, prefix="/internal/gpu")
