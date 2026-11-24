/**
 * Capa: Data (Feature: auth)
 * Responsabilidad: Implementación usando el SDK de Supabase Auth.
 * Regla de Dependencia: Implementa AuthRepository de la capa Domain y encapsula
 *   el cliente de Supabase Auth. Tiene prohibido importar componentes de la
 *   capa Presentation (LoginPage).
 */
import '../../domain/repositories/auth_repository.dart';

class SupabaseAuthRepositoryImpl implements AuthRepository {
  @override
  Future<void> signInWithEmail(String email, String password) async {}

  @override
  Future<void> signUpWithEmail(String email, String password, String username) async {}

  @override
  Future<void> signOut() async {}

  @override
  String? getCurrentAccessToken() => null;
}
