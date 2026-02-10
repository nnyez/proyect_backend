package ups.edu.ec.proyect_backend.users.dtos.developer;

import java.util.Set;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * DTO para actualización parcial (PATCH) de perfil de usuario DEVELOPER/ADMIN
 * Campos completos pero todos opcionales para PATCH
 */
public class PatchDeveloperProfileDto {

    @Size(max = 500, message = "La URL de foto no puede exceder 500 caracteres")
    private String photoUrl;

    @Size(max = 100, message = "El número de teléfono no puede exceder 100 caracteres")
    private String phoneNumber;

    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    private String title;

    @Size(max = 250, message = "La biografía no puede exceder 250 caracteres")
    private String bio;

    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    private Integer experienceYears;

    private Set<Long> skillIds; // IDs de tecnologías

    // ============ CONSTRUCTORS ============
    public PatchDeveloperProfileDto() {
    }

    public PatchDeveloperProfileDto(String photoUrl, String phoneNumber, String title, String bio,
            Integer experienceYears, Set<Long> skillIds) {
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.bio = bio;
        this.experienceYears = experienceYears;
        this.skillIds = skillIds;
    }

    // ============ GETTERS AND SETTERS ============
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

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Set<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(Set<Long> skillIds) {
        this.skillIds = skillIds;
    }
}
