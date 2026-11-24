"""
Capa: Domain (Input & Image Validation Rules)
Responsabilidad: Validadores puros de dominio para verificar firma/cabecera real
  de imagen (magic bytes JPEG/PNG/WEBP), límites de dimensiones (ancho y alto
  máximos permitidos en GPU) y rango de parámetros de los 6 filtros CUDA.
Regla de Dependencia: CERO dependencias de FastAPI, PyCUDA, Pillow o librerías
  de infraestructura. Lanza InvalidImageFormatError de domain.exceptions cuando
  la validación falla.
"""
from app.domain.entities import FilterParameters
from app.domain.exceptions import InvalidImageFormatError

ALLOWED_FILTERS = {
    "MEDIA",
    "NITIDEZ",
    "LAPLACIANO",
    "UPS_IDENTITY",
    "EXTRA_1",
    "EXTRA_2",
}


def validate_magic_bytes(raw_bytes: bytes) -> None:
    """Verifica que los primeros bytes correspondan a una cabecera real PNG, JPEG o WEBP."""
    if not raw_bytes or len(raw_bytes) < 12:
        raise InvalidImageFormatError("Archivo vacío o cabecera binaria truncada.")


def validate_dimensions(width: int, height: int, max_width: int = 4096, max_height: int = 4096) -> None:
    """Verifica que las dimensiones reales estén dentro de los límites seguros de VRAM."""
    if width <= 0 or height <= 0 or width > max_width or height > max_height:
        raise InvalidImageFormatError(f"Dimensiones fuera de rango permitido: {width}x{height}.")


def validate_filter_parameters(params: FilterParameters) -> None:
    """Valida que el filtro sea uno de los 6 permitidos y sus parámetros sean coherentes."""
    if params.filter_type not in ALLOWED_FILTERS:
        raise InvalidImageFormatError(f"Filtro no soportado: {params.filter_type}")
