package ups.edu.ec.proyect_backend.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import ups.edu.ec.proyect_backend.auth.models.Rol;
import ups.edu.ec.proyect_backend.core.Entities.BaseEntity;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;

@Entity
@Table(name = "user_auth")
public class UserAuthEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private UserProfileEntity profile;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    // ============ CONSTRUCTORS ============
    public UserAuthEntity() {
    }

    public UserAuthEntity(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // ============ GETTERS AND SETTERS ============

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(UserProfileEntity profile) {
        this.profile = profile;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}