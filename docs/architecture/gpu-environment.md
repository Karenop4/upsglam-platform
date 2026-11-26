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
- Al iniciar `ms-gpu-pycuda` o al invocar `GET /internal/gpu/health` y `GET /internal/gpu/health/gpu`, `CudaContextManager` inicializa `pycuda.driver.init()` y verifica `Device.count() > 0`.
- Si no hay GPU accesible en el contenedor, el servicio lanza `GpuUnavailableError` y responde `HTTP 503 Service Unavailable` con diagnóstico explícito. Nunca procesa filtros en CPU.

## 3. Imagen y despliegue
- **Imagen base:** `nvidia/cuda:13.4.3-devel-ubuntu24.04` (incluye `nvcc`, requerido por PyCUDA para compilar los kernels `.cu` en tiempo de ejecución). CUDA 13.x soporta Blackwell (RTX 50xx, compute capability 12.0).
- **Usuario sin privilegios:** `app` (uid 10001); `no-new-privileges` y `cap_drop: ALL`.
- **Red aislada:** el servicio solo está en la red `gpu-internal` (`internal: true`), sin salida a internet y alcanzable únicamente por `ms-processing`. El API Gateway no lo enruta.
- **Healthcheck:** `GET /internal/gpu/health` cada 15 s.
