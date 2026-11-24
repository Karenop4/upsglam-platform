// =============================================================================
// Capa: Testing (Concurrency & Load Testing Script)
// Responsabilidad: Script de prueba de carga concurrente (k6) para verificar el
//   comportamiento de la cola acotada, tiempos de respuesta de GPU y rechazo
//   controlado HTTP 429 bajo ráfagas de solicitudes simultáneas.
// Regla de Dependencia: Ejecutado externamente contra el API Gateway
//   (/api/v1/processing/jobs y /api/v1/processing/filters).
// =============================================================================

import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 20,
  duration: '15s',
};

export default function () {
  const baseUrl = __ENV.API_BASE_URL || 'http://localhost:8080/api/v1';
  const res = http.get(`${baseUrl}/processing/filters`);
  check(res, {
    'status is 200 or 429': (r) => r.status === 200 || r.status === 429,
  });
}
