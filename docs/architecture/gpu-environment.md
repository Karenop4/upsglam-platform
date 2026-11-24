<!--
Capa: Infrastructure / Architecture Documentation
Responsabilidad: Documentar los requisitos de entorno NVIDIA GPU, drivers CUDA,
  NVIDIA Container Toolkit y diagnóstico sin fallback silencioso a CPU.
Regla de Dependencia: Documentación técnica de infraestructura vinculada a
  docker-compose.yml y services/ms-gpu-pycuda/app/infrastructure/cuda/context_manager.py.
-->
# Entorno de Ejecución GPU (NVIDIA CUDA & PyCUDA)

## 1. Requisitos del Host
- **GPU**: Tarjeta NVIDIA con soporte CUDA (Compute Capability >= 5.0).
- **Driver NVIDIA**: Instalado en el host (`nvidia-smi` operativo).
- **NVIDIA Container Toolkit**: Configurado en Docker Engine para habilitar `capabilities: [gpu]` en `docker-compose.yml`.

## 2. Política Estricta: Prohibido Fallback Silencioso a CPU
- Al iniciar `ms-gpu-pycuda` o al invocar `GET /health` y `GET /health/gpu`, `CudaContextManager` inicializa `pycuda.driver.init()` y verifica `Device.count() > 0`.
- Si no hay GPU accesible en el contenedor, el servicio lanza `GpuUnavailableError` y responde `HTTP 503 Service Unavailable` con diagnóstico explícito. Nunca procesa filtros en CPU.
