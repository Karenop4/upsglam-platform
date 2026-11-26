-- =============================================================================
-- Capa: Infrastructure (Persistence / RLS Hardening)
-- Responsabilidad: Impedir que un usuario autenticado modifique directamente
--   sus propias processing_requests vía PostgREST (p. ej. marcar status =
--   'COMPLETED' o cambiar processed_image_url sin haber pasado por la GPU).
--   Las transiciones de estado quedan reservadas al backend (ms-processing con
--   service_role, que no está sujeto a RLS).
-- Regla de Dependencia: Se ejecuta después de 001_initial_schema.sql. No
--   modifica datos existentes.
-- =============================================================================

DROP POLICY IF EXISTS "processing_requests_update_owner" ON public.processing_requests;
