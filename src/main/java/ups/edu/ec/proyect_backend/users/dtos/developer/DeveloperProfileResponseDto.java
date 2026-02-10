package ups.edu.ec.proyect_backend.users.dtos.developer;

import java.util.Set;

/**
 * DTO para respuestas de perfil de usuario DEVELOPER/ADMIN
 * Campos completos: incluye skills, experiencia y todos los detalles
 */
public class DeveloperProfileResponseDto {

    private Long id; // ID del perfil
    private Long userId; // ID del usuario autenticado
    private String email; // Del UserAuth
    private String name; // Del UserAuth
    private String photoUrl;
    private String phoneNumber;
    private String title;
    private String bio;
    private Integer experienceYears;
    private Set<SkillResponseDto> skills;

    // ============ CONSTRUCTORS ============
    public DeveloperProfileResponseDto() {
    }

    public DeveloperProfileResponseDto(Long id, Long userId, String email, String name,
            String photoUrl, String phoneNumber, String title, String bio,
            Integer experienceYears, Set<SkillResponseDto> skills) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.bio = bio;
        this.experienceYears = experienceYears;
        this.skills = skills;
    }

    // ============ GETTERS AND SETTERS ============
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Set<SkillResponseDto> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillResponseDto> skills) {
        this.skills = skills;
    }

    // ============ INNER CLASS - SkillResponseDto ============
    /**
     * DTO simplificado para respuestas de tecnologías/skills
     */
    public static class SkillResponseDto {
        private Long id;
        private String technology;

        public SkillResponseDto() {
        }

        public SkillResponseDto(Long id, String technology) {
            this.id = id;
            this.technology = technology;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTechnology() {
            return technology;
        }

        public void setTechnology(String technology) {
            this.technology = technology;
        }
    }
}
