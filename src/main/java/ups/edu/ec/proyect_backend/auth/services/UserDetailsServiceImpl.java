package ups.edu.ec.proyect_backend.auth.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ups.edu.ec.proyect_backend.auth.entities.UserAuthEntity;
import ups.edu.ec.proyect_backend.auth.repositories.UserAuthRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    public UserDetailsServiceImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuthEntity userAuth = userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return UserDetailsImpl.build(userAuth);
    }
}
