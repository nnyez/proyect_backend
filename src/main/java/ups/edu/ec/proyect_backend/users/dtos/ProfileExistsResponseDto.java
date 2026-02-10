package ups.edu.ec.proyect_backend.users.dtos;

public class ProfileExistsResponseDto {

    private boolean exists;
    private Long profileId;

    // Constructores
    public ProfileExistsResponseDto() {
    }

    public ProfileExistsResponseDto(boolean exists, Long profileId) {
        this.exists = exists;
        this.profileId = profileId;
    }

    // Getters y Setters
    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
