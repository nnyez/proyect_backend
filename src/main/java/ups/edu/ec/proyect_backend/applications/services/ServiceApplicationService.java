package ups.edu.ec.proyect_backend.applications.services;

import ups.edu.ec.proyect_backend.applications.dtos.ApplicationResponseDto;
import ups.edu.ec.proyect_backend.applications.dtos.CreateApplicationDto;
import ups.edu.ec.proyect_backend.applications.dtos.UpdateApplicationStatusDto;
import ups.edu.ec.proyect_backend.applications.models.ApplicationStatus;

import java.util.List;

/**
 * Interfaz de servicio para gestionar solicitudes de servicio
 */
public interface ServiceApplicationService {
    
    /**
     * Crea una nueva solicitud de servicio
     * @param clientId ID del cliente que crea la solicitud
     * @param createDto Datos de la solicitud
     * @return Solicitud creada
     */
    ApplicationResponseDto createApplication(Long clientId, CreateApplicationDto createDto);
    
    /**
     * Obtiene una solicitud por su ID
     * @param id ID de la solicitud
     * @return Solicitud encontrada
     */
    ApplicationResponseDto getApplicationById(Long id);
    
    /**
     * Obtiene todas las solicitudes creadas por un cliente
     * @param clientId ID del cliente
     * @return Lista de solicitudes del cliente
     */
    List<ApplicationResponseDto> getApplicationsByClient(Long clientId);
    
    /**
     * Obtiene todas las solicitudes dirigidas a un programador
     * @param programmerId ID del programador
     * @return Lista de solicitudes para el programador
     */
    List<ApplicationResponseDto> getApplicationsByProgrammer(Long programmerId);
    
    /**
     * Obtiene solicitudes de un cliente filtradas por estado
     * @param clientId ID del cliente
     * @param status Estado de la solicitud
     * @return Lista de solicitudes filtradas
     */
    List<ApplicationResponseDto> getApplicationsByClientAndStatus(Long clientId, ApplicationStatus status);
    
    /**
     * Obtiene solicitudes para un programador filtradas por estado
     * @param programmerId ID del programador
     * @param status Estado de la solicitud
     * @return Lista de solicitudes filtradas
     */
    List<ApplicationResponseDto> getApplicationsByProgrammerAndStatus(Long programmerId, ApplicationStatus status);
    
    /**
     * Actualiza el estado de una solicitud
     * @param id ID de la solicitud
     * @param updateDto Datos de actualización
     * @return Solicitud actualizada
     */
    ApplicationResponseDto updateApplicationStatus(Long id, UpdateApplicationStatusDto updateDto);
    
    /**
     * Elimina una solicitud
     * @param id ID de la solicitud
     */
    void deleteApplication(Long id);
}
