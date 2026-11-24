/**
 * Capa: Data (Feature: studio)
 * Responsabilidad: Llamadas HTTP al Gateway (ms-processing) para enviar imagen
 *   y consultar estado.
 * Regla de Dependencia: Utiliza DioClient de la capa Core para comunicarse con
 *   el API Gateway. Tiene prohibido importar widgets de Flutter o ser invocado
 *   directamente desde StudioPage sin pasar por la capa de dominio/repositorio.
 */
import '../../../../core/network/dio_client.dart';

class ProcessingRemoteDatasource {
  final DioClient dioClient;

  ProcessingRemoteDatasource({required this.dioClient});
}
