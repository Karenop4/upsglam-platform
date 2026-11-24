/**
 * Capa: Application (Use Case Implementation)
 * Responsabilidad: Orquestador reactivo no bloqueante: sube imagen original,
 *   invoca GPU, guarda resultado en Storage, persiste métricas/historial y
 *   maneja fallos sin inventar métricas.
 * Regla de Dependencia: Implementa ProcessImageUseCasePort y coordina los
 *   puertos de salida StoragePort, GpuServiceClientPort y JobRepositoryPort.
 *   Prohibido importar controladores HTTP, clientes WebClient concretos o
 *   repositorios R2DBC de la capa Infrastructure.
 */
package ec.edu.ups.processing.application.usecase;

import ec.edu.ups.processing.domain.port.in.ProcessImageUseCasePort;
import ec.edu.ups.processing.domain.port.out.GpuServiceClientPort;
import ec.edu.ups.processing.domain.port.out.JobRepositoryPort;
import ec.edu.ups.processing.domain.port.out.StoragePort;

public class ProcessImageUseCaseImpl implements ProcessImageUseCasePort {

    private final StoragePort storagePort;
    private final GpuServiceClientPort gpuServiceClientPort;
    private final JobRepositoryPort jobRepositoryPort;

    public ProcessImageUseCaseImpl(
            StoragePort storagePort,
            GpuServiceClientPort gpuServiceClientPort,
            JobRepositoryPort jobRepositoryPort
    ) {
        this.storagePort = storagePort;
        this.gpuServiceClientPort = gpuServiceClientPort;
        this.jobRepositoryPort = jobRepositoryPort;
    }
}
