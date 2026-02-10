package ups.edu.ec.proyect_backend.auth.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ups.edu.ec.proyect_backend.auth.entities.UserAuthEntity;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Long> {
    Optional<UserAuthEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
