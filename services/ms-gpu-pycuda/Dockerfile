# ==============================================================================
# Capa: Infrastructure (GPU Service Container Build Definition)
# Responsabilidad: Definir la imagen Docker de ms-gpu-pycuda con Python,
#   FastAPI, Uvicorn y soporte para PyCUDA/CUDA, lista para ejecutarse con
#   NVIDIA Container Toolkit.
# Regla de Dependencia: Empaqueta únicamente el servicio ms-gpu-pycuda, sus
#   kernels .cu y el asset institucional assets/ups_logo.png.
# ==============================================================================

FROM python:3.11-slim

WORKDIR /workspace

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY app/ ./app/
COPY assets/ ./assets/

EXPOSE 8000

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
