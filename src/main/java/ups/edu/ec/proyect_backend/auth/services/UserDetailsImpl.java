package ups.edu.ec.proyect_backend.auth.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ups.edu.ec.proyect_backend.auth.entities.UserAuthEntity;

public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String email;
    private final String name;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String email, String name, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Factory method para crear UserDetailsImpl desde UserAuthEntity
     */
    public static UserDetailsImpl build(UserAuthEntity userAuth) {
        // Convertir rol a authority de Spring Security
        Collection<GrantedAuthority> authorities = java.util.Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + userAuth.getRol().name()));

        return new UserDetailsImpl(
                userAuth.getId(),
                userAuth.getEmail(),
                userAuth.getName(),
                userAuth.getPassword(),
                authorities);
    }

    // ============== GETTERS ==============

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    // ============== MÉTODOS DE UserDetails ==============

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Usamos email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
