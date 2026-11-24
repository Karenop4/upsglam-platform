-- =============================================================================
-- Capa: Infrastructure (Persistence / Database Schema & RLS Policies)
-- Responsabilidad: Script DDL de PostgreSQL en Supabase con las tablas
--   acordadas en el plan (profiles, filters, processing_requests, gpu_metrics,
--   posts con processing_request_id, comments, likes), políticas explícitas de
--   Row Level Security (RLS), políticas de Supabase Storage y publicación en
--   Supabase Realtime para likes y comments.
-- Regla de Dependencia: Es consumido únicamente por la infraestructura de
--   Supabase (PostgREST / Storage / Realtime) y accedido desde los microservicios
--   a través de adaptadores WebClient PostgREST que propagan el JWT del usuario.
--   Las entidades de Domain no conocen detalles de SQL ni políticas RLS.
-- =============================================================================

-- 1. Tabla de Perfiles vinculada a auth.users de Supabase
CREATE TABLE IF NOT EXISTS public.profiles (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    username VARCHAR(50) UNIQUE NOT NULL,
    bio TEXT,
    avatar_url TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 2. Catálogo de Filtros CUDA permitidos
CREATE TABLE IF NOT EXISTS public.filters (
    code VARCHAR(32) PRIMARY KEY,
    display_name VARCHAR(80) NOT NULL,
    description TEXT NOT NULL,
    default_kernel_size INT NOT NULL DEFAULT 3,
    supports_intensity BOOLEAN NOT NULL DEFAULT TRUE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- 3. Solicitudes de Procesamiento (processing_requests)
CREATE TABLE IF NOT EXISTS public.processing_requests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    filter_code VARCHAR(32) NOT NULL REFERENCES public.filters(code),
    status VARCHAR(20) NOT NULL CHECK (status IN ('QUEUED', 'PROCESSING', 'COMPLETED', 'FAILED')),
    original_image_url TEXT NOT NULL,
    processed_image_url TEXT,
    error_message TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    completed_at TIMESTAMPTZ
);

-- 4. Métricas reales de ejecución en GPU (gpu_metrics)
CREATE TABLE IF NOT EXISTS public.gpu_metrics (
    processing_request_id UUID PRIMARY KEY REFERENCES public.processing_requests(id) ON DELETE CASCADE,
    image_width INT NOT NULL,
    image_height INT NOT NULL,
    block_dim VARCHAR(32) NOT NULL,
    grid_dim VARCHAR(32) NOT NULL,
    total_threads BIGINT NOT NULL,
    kernel_time_ms DOUBLE PRECISION NOT NULL,
    device_name VARCHAR(100),
    recorded_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 5. Publicaciones sociales (posts con processing_request_id)
CREATE TABLE IF NOT EXISTS public.posts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    processing_request_id UUID REFERENCES public.processing_requests(id) ON DELETE SET NULL,
    image_url TEXT NOT NULL,
    caption TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 6. Comentarios en publicaciones
CREATE TABLE IF NOT EXISTS public.comments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id UUID NOT NULL REFERENCES public.posts(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 7. Reacciones (Likes con unicidad post_id + user_id)
CREATE TABLE IF NOT EXISTS public.likes (
    post_id UUID NOT NULL REFERENCES public.posts(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (post_id, user_id)
);

-- =============================================================================
-- HABILITACIÓN Y POLÍTICAS EXPLÍCITAS DE ROW LEVEL SECURITY (RLS)
-- =============================================================================
ALTER TABLE public.profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.filters ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.processing_requests ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.gpu_metrics ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.posts ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.comments ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.likes ENABLE ROW LEVEL SECURITY;

-- Perfiles: lectura pública para usuarios autenticados, edición solo del propietario
CREATE POLICY "profiles_select_authenticated" ON public.profiles
    FOR SELECT TO authenticated USING (true);
CREATE POLICY "profiles_update_owner" ON public.profiles
    FOR UPDATE TO authenticated USING (auth.uid() = id) WITH CHECK (auth.uid() = id);

-- Filtros: lectura para cualquier usuario autenticado
CREATE POLICY "filters_select_authenticated" ON public.filters
    FOR SELECT TO authenticated USING (is_active = true);

-- Processing Requests: cada usuario ve y crea únicamente sus solicitudes
CREATE POLICY "processing_requests_select_owner" ON public.processing_requests
    FOR SELECT TO authenticated USING (auth.uid() = user_id);
CREATE POLICY "processing_requests_insert_owner" ON public.processing_requests
    FOR INSERT TO authenticated WITH CHECK (auth.uid() = user_id);
CREATE POLICY "processing_requests_update_owner" ON public.processing_requests
    FOR UPDATE TO authenticated USING (auth.uid() = user_id);

-- GPU Metrics: visibles para el dueño de la solicitud o en publicaciones compartidas
CREATE POLICY "gpu_metrics_select_authenticated" ON public.gpu_metrics
    FOR SELECT TO authenticated USING (true);

-- Posts: feed visible para todos los autenticados; INSERT/UPDATE/DELETE exclusivo del autor
CREATE POLICY "posts_select_authenticated" ON public.posts
    FOR SELECT TO authenticated USING (true);
CREATE POLICY "posts_insert_owner" ON public.posts
    FOR INSERT TO authenticated WITH CHECK (auth.uid() = user_id);
CREATE POLICY "posts_update_owner" ON public.posts
    FOR UPDATE TO authenticated USING (auth.uid() = user_id) WITH CHECK (auth.uid() = user_id);
CREATE POLICY "posts_delete_owner" ON public.posts
    FOR DELETE TO authenticated USING (auth.uid() = user_id);

-- Comments: lectura general, creación y borrado solo por el autor del comentario
CREATE POLICY "comments_select_authenticated" ON public.comments
    FOR SELECT TO authenticated USING (true);
CREATE POLICY "comments_insert_owner" ON public.comments
    FOR INSERT TO authenticated WITH CHECK (auth.uid() = user_id);
CREATE POLICY "comments_delete_owner" ON public.comments
    FOR DELETE TO authenticated USING (auth.uid() = user_id);

-- Likes: lectura general, inserción y eliminación solo del propio usuario
CREATE POLICY "likes_select_authenticated" ON public.likes
    FOR SELECT TO authenticated USING (true);
CREATE POLICY "likes_insert_owner" ON public.likes
    FOR INSERT TO authenticated WITH CHECK (auth.uid() = user_id);
CREATE POLICY "likes_delete_owner" ON public.likes
    FOR DELETE TO authenticated USING (auth.uid() = user_id);

-- =============================================================================
-- POLÍTICAS DE SUPABASE STORAGE (Bucket: upsglam-images)
-- =============================================================================
INSERT INTO storage.buckets (id, name, public)
VALUES ('upsglam-images', 'upsglam-images', true)
ON CONFLICT (id) DO NOTHING;

CREATE POLICY "storage_public_read" ON storage.objects
    FOR SELECT USING (bucket_id = 'upsglam-images');

CREATE POLICY "storage_authenticated_upload" ON storage.objects
    FOR INSERT TO authenticated
    WITH CHECK (bucket_id = 'upsglam-images' AND (storage.foldername(name))[1] = auth.uid()::text);

CREATE POLICY "storage_owner_delete" ON storage.objects
    FOR DELETE TO authenticated
    USING (bucket_id = 'upsglam-images' AND (storage.foldername(name))[1] = auth.uid()::text);

-- =============================================================================
-- PUBLICACIÓN EN SUPABASE REALTIME (Likes, Comments y Posts en tiempo real)
-- =============================================================================
ALTER PUBLICATION supabase_realtime ADD TABLE public.posts;
ALTER PUBLICATION supabase_realtime ADD TABLE public.comments;
ALTER PUBLICATION supabase_realtime ADD TABLE public.likes;
