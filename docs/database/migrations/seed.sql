-- =============================================================================
-- Capa: Infrastructure (Database Seed Data)
-- Responsabilidad: Poblar el catálogo inicial con los 6 filtros CUDA
--   obligatorios de UPSGlam (MEDIA, NITIDEZ, LAPLACIANO, UPS_IDENTITY,
--   EXTRA_1 [Sobel Magnitude], EXTRA_2 [Sepia/Emboss direccional]).
-- Regla de Dependencia: Script SQL idempotente ejecutado después de
--   001_initial_schema.sql sobre PostgreSQL de Supabase.
-- =============================================================================

INSERT INTO public.filters (code, display_name, description, default_kernel_size, supports_intensity, is_active)
VALUES
    ('MEDIA', 'Filtro de Media (Box Blur)', 'Suavizado espacial por promedio de vecindad 2D en GPU CUDA.', 5, false, true),
    ('NITIDEZ', 'Filtro de Nitidez (Sharpen)', 'Realce de detalles de alta frecuencia mediante convolución 2D en CUDA.', 3, true, true),
    ('LAPLACIANO', 'Filtro Laplaciano (Bordes)', 'Detección omnidireccional de bordes de segundo orden en GPU.', 3, false, true),
    ('UPS_IDENTITY', 'Identidad UPS (Institucional)', 'Transformación duotono Azul/Oro institucional UPS con composición de viñeta y sello en GPU.', 3, true, true),
    ('EXTRA_1', 'Filtro Sobel Gradiente 2D', 'Cálculo no lineal de magnitud de gradiente ortogonal (Gx, Gy) para contornos luminosos.', 3, true, true),
    ('EXTRA_2', 'Filtro Relieve Cálido (Emboss)', 'Transformación direccional de relieve asimétrico con desplazamiento tonal en GPU.', 3, true, true)
ON CONFLICT (code) DO UPDATE SET
    display_name = EXCLUDED.display_name,
    description = EXCLUDED.description,
    default_kernel_size = EXCLUDED.default_kernel_size,
    supports_intensity = EXCLUDED.supports_intensity,
    is_active = EXCLUDED.is_active;
