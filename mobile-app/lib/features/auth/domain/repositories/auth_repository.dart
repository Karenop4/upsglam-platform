/**
 * Capa: Domain (Feature: auth)
 * Responsabilidad: Contrato de registro, login, logout y sesión activa.
 * Regla de Dependencia: CERO dependencias de Supabase SDK, Dio o Flutter UI.
 *   Es implementado en la capa Data (SupabaseAuthRepositoryImpl) y consumido
 *   por casos de uso o gestores de estado de Presentation.
 */
abstract class AuthRepository {
  Future<void> signInWithEmail(String email, String password);
  Future<void> signUpWithEmail(String email, String password, String username);
  Future<void> signOut();
  String? getCurrentAccessToken();
}
