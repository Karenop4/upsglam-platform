/**
 * Capa: Data (Feature: feed)
 * Responsabilidad: Consumo de ms-social para feed cronológico, CRUD propio,
 *   likes y comentarios en tiempo real.
 * Regla de Dependencia: Utiliza DioClient de la capa Core para consumir los
 *   endpoints REST y SSE/WebSocket expuestos por el API Gateway hacia
 *   ms-social. Prohibido acoplar componentes de UI (FeedPage).
 */
import '../../../../core/network/dio_client.dart';

class SocialRemoteDatasource {
  final DioClient dioClient;

  SocialRemoteDatasource({required this.dioClient});
}
