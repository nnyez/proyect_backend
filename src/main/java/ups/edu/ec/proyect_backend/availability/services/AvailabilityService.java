package ups.edu.ec.proyect_backend.availability.services;

import ups.edu.ec.proyect_backend.availability.dtos.AvailabilityResponseDto;
import ups.edu.ec.proyect_backend.availability.dtos.CreateAvailabilityDto;

/**
 * Interfaz de servicio para gestionar la disponibilidad de usuarios
 */
public interface AvailabilityService {
    
    /**
     * Crea o actualiza la configuración de disponibilidad de un usuario
     * @param userId ID del usuario
     * @param createDto Datos de disponibilidad
     * @return Configuración guardada
     */
    AvailabilityResponseDto createOrUpdateAvailability(Long userId, CreateAvailabilityDto createDto);
    
    /**
     * Obtiene la configuración de disponibilidad de un usuario
     * @param userId ID del usuario
     * @return Configuración de disponibilidad
     */
    AvailabilityResponseDto getAvailabilityByUserId(Long userId);
    
    /**
     * Elimina la configuración de disponibilidad de un usuario
     * @param userId ID del usuario
     */
    void deleteAvailability(Long userId);
}
