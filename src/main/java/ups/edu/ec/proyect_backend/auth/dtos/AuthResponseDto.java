package ups.edu.ec.proyect_backend.auth.dtos;

public class AuthResponseDto {

    private String token;
    private String type = "Bearer";
    private Long userId;
    private String name;
    private String email;
    private String rol;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String token, Long userId, String name, String email, String rol) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.rol = rol;
    }

    public AuthResponseDto(String token, Long userId, String email, String rol) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
