package ups.edu.ec.proyect_backend.technologies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ups.edu.ec.proyect_backend.technologies.entities.TechnologyEntity;

import java.util.Optional;

@Repository
public interface TechnologyRepository extends JpaRepository<TechnologyEntity, Long> {
	Optional<TechnologyEntity> findByTechnologyIgnoreCase(String technology);
}
