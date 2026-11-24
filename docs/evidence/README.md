<!--
Capa: Testing & Verification Evidence
Responsabilidad: Directorio de evidencias de pruebas de integración, capturas
  de nvidia-smi, tiempos de kernel CUDA y resultados de pruebas de carga.
Regla de Dependencia: Almacena artefactos verificables para la defensa técnica.
-->
# Evidencias de Ejecución y Defensa Técnica (UPSGlam)

En este directorio se registran:
1. Capturas y logs de `GET /internal/gpu/health` con hardware NVIDIA activo.
2. Tiempos reales de ejecución de kernels medidos con `pycuda.driver.Event`.
3. Reportes de pruebas de concurrencia y rechazo controlado HTTP 429 generados desde `load-tests/`.
