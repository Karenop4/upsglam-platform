/**
 * Capa: Domain (Feature: feed)
 * Responsabilidad: Entidad de publicación para la capa de presentación.
 * Regla de Dependencia: CERO dependencias de paquetes externos (Dio, Supabase,
 *   Flutter UI). Representa el modelo puro de negocio de una publicación social
 *   con su imagen procesada en GPU.
 */
class PostEntity {
  final String id;
  final String userId;
  final String imageUrl;
  final String? caption;
  final DateTime createdAt;

  const PostEntity({
    required this.id,
    required this.userId,
    required this.imageUrl,
    this.caption,
    required this.createdAt,
  });
}
