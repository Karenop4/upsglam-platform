<!--
Capa: Application / Architecture Documentation
Responsabilidad: Documentar la política explícita de concurrencia, cola acotada,
  rechazo controlado HTTP 429 y recuperación de trabajos PROCESSING huérfanos.
Regla de Dependencia: Especifica el comportamiento de ConcurrencyManager en
  ms-gpu-pycuda y OrphanJobRecoveryListener / QueuePolicy en ms-processing.
-->
# Política de Concurrencia y Recuperación de Fallos

## 1. Control de Concurrencia en GPU (`ms-gpu-pycuda`)
- **Semáforo de Ejecución Activa (`GPU_MAX_CONCURRENT_TASKS`)**: Limita el número de kernels simultáneos que reservan memoria VRAM.
- **Cola Acotada (`GPU_QUEUE_MAX_SIZE`)**: Las solicitudes adicionales esperan en una cola FIFO acotada con timeout (`GPU_TASK_TIMEOUT_SECONDS`).
- **Rechazo Controlado (`HTTP 429 Too Many Requests`)**: Si la cola alcanza su capacidad máxima, `ConcurrencyManager` lanza `QueueCapacityExceededError` de inmediato para evitar saturación o caída por OOM en la GPU.

## 2. Recuperación de `PROCESSING` Huérfanos (`ms-processing`)
- Al iniciar `ms-processing`, el componente `OrphanJobRecoveryListener` consulta en Supabase (`processing_requests`) los registros que quedaron en estado `PROCESSING` o `QUEUED` por un reinicio inesperado y los marca como `FAILED` con motivo de interrupción de nodo, sin inventar métricas GPU.
