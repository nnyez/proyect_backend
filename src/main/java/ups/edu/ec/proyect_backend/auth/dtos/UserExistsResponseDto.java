package ups.edu.ec.proyect_backend.auth.dtos;

public class UserExistsResponseDto {

    private boolean exists;
    private Long userId;

    public UserExistsResponseDto() {
    }

    public UserExistsResponseDto(boolean exists, Long userId) {
        this.exists = exists;
        this.userId = userId;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
