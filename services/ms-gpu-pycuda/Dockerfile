# syntax=docker/dockerfile:1.7
# ==============================================================================
# Capa: Infrastructure (GPU Service Container Build Definition)
# Responsabilidad: Imagen de ms-gpu-pycuda sobre CUDA *devel* (incluye nvcc,
#   necesario para que PyCUDA compile los kernels .cu en tiempo de ejecución),
#   con FastAPI/Uvicorn en un virtualenv y usuario sin privilegios.
#   CUDA 13.x soporta la arquitectura Blackwell (RTX 50xx, sm_120).
# Regla de Dependencia: Empaqueta únicamente ms-gpu-pycuda, sus kernels .cu y
#   el asset institucional assets/ups_logo.png. Requiere NVIDIA Container
#   Toolkit en el host.
# ==============================================================================

ARG CUDA_IMAGE=nvidia/cuda:13.4.3-devel-ubuntu24.04
FROM ${CUDA_IMAGE}

ENV DEBIAN_FRONTEND=noninteractive \
    PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1 \
    VIRTUAL_ENV=/opt/venv \
    PATH=/opt/venv/bin:/usr/local/cuda/bin:$PATH

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        python3 python3-venv python3-dev build-essential \
    && rm -rf /var/lib/apt/lists/* \
    && python3 -m venv "$VIRTUAL_ENV"

WORKDIR /workspace

COPY requirements.txt .
RUN --mount=type=cache,target=/root/.cache/pip \
    pip install --upgrade pip \
    && pip install -r requirements.txt

RUN groupadd --system --gid 10001 app \
    && useradd --system --uid 10001 --gid app --create-home --home-dir /home/app --shell /usr/sbin/nologin app

COPY --chown=app:app app/ ./app/
COPY --chown=app:app assets/ ./assets/

USER app:app
EXPOSE 8000

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000", "--no-server-header"]
