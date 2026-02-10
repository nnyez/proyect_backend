package ups.edu.ec.proyect_backend.projects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ups.edu.ec.proyect_backend.projects.entities.ProjectEntity;

import java.util.List;

/**
 * Repositorio JPA para proyectos
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    
    /**
     * Buscar proyectos por ID del propietario
     * @param ownerId ID del propietario
     * @return Lista de proyectos
     */
    List<ProjectEntity> findByOwnerId(Long ownerId);
}
