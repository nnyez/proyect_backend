package ups.edu.ec.proyect_backend.availability.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ups.edu.ec.proyect_backend.availability.entities.DayAvailabilityEntity;

import java.util.List;

/**
 * Repositorio para operaciones de disponibilidad por día
 */
@Repository
public interface DayAvailabilityRepository extends JpaRepository<DayAvailabilityEntity, Long> {
    
    /**
     * Busca todas las disponibilidades de un usuario específico
     * @param userId ID del usuario
     * @return Lista de disponibilidades del usuario
     */
    List<DayAvailabilityEntity> findByUserId(Long userId);
    
    /**
     * Elimina todas las disponibilidades de un usuario específico
     * @param userId ID del usuario
     */
    void deleteByUserId(Long userId);
}
