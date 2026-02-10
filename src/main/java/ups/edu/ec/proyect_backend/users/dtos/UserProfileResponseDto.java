package ups.edu.ec.proyect_backend.users.dtos;

import java.util.Set;

public class UserProfileResponseDto {

    private Long id;
    private String photoUrl;
    private String phoneNumber;
    private String title;
    private String bio;
    private Set<Long> skillIds;
    private Integer experienceYears;
    private String userName;
    private String userEmail;

    // Constructores
    public UserProfileResponseDto() {
    }

    public UserProfileResponseDto(Long id, String photoUrl, String phoneNumber, String title, String bio,
            Set<Long> skillIds, Integer experienceYears, String userName, String userEmail) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.bio = bio;
        this.skillIds = skillIds;
        this.experienceYears = experienceYears;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(Set<Long> skillIds) {
        this.skillIds = skillIds;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
