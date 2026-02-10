package ups.edu.ec.proyect_backend.users.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import ups.edu.ec.proyect_backend.auth.entities.UserAuthEntity;
import ups.edu.ec.proyect_backend.core.Entities.BaseEntity;
import ups.edu.ec.proyect_backend.technologies.entities.TechnologyEntity;

@Entity
@Table(name = "user_profile")
public class UserProfileEntity extends BaseEntity {

    @OneToOne(mappedBy = "profile")
    private UserAuthEntity auth;

    @Column(length = 500)
    private String photoUrl;
    @Column(length = 100)
    private String phoneNumber;

    // Optional
    @Column(length = 100)
    private String title;
    @Column(length = 250)
    private String bio;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<TechnologyEntity> skills = new HashSet<>();
    @Column
    private Integer experienceYears;

    // ============ CONSTRUCTORS ============
    public UserProfileEntity() {
    }

    // ============ GETTERS AND SETTERS ============

    public Long getId() {
        return super.id;
    }

    public UserAuthEntity getAuth() {
        return auth;
    }

    public void setAuth(UserAuthEntity auth) {
        this.auth = auth;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<TechnologyEntity> getSkills() {
        return skills;
    }

    public void setSkills(Set<TechnologyEntity> skills) {
        this.skills = skills;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
}
