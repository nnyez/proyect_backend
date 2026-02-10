package ups.edu.ec.proyect_backend.applications.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ups.edu.ec.proyect_backend.applications.entities.ServiceApplicationEntity;
import ups.edu.ec.proyect_backend.applications.models.ApplicationStatus;

import java.util.List;

/**
 * Repositorio para operaciones de solicitudes de servicio
 */
@Repository
public interface ServiceApplicationRepository extends JpaRepository<ServiceApplicationEntity, Long> {
    
    /**
     * Busca todas las solicitudes creadas por un cliente específico
     * @param clientId ID del cliente
     * @return Lista de solicitudes del cliente
     */
    List<ServiceApplicationEntity> findByClientId(Long clientId);
    
    /**
     * Busca todas las solicitudes dirigidas a un programador específico
     * @param programmerId ID del programador
     * @return Lista de solicitudes para el programador
     */
    List<ServiceApplicationEntity> findByProgrammerId(Long programmerId);
    
    /**
     * Busca solicitudes de un cliente con un estado específico
     * @param clientId ID del cliente
     * @param status Estado de la solicitud
     * @return Lista de solicitudes filtradas
     */
    List<ServiceApplicationEntity> findByClientIdAndStatus(Long clientId, ApplicationStatus status);
    
    /**
     * Busca solicitudes para un programador con un estado específico
     * @param programmerId ID del programador
     * @param status Estado de la solicitud
     * @return Lista de solicitudes filtradas
     */
    List<ServiceApplicationEntity> findByProgrammerIdAndStatus(Long programmerId, ApplicationStatus status);
}
