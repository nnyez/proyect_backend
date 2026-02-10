package ups.edu.ec.proyect_backend.users.dtos.standard;

/**
 * DTO para respuestas de perfil de usuario STANDARD
 * Campos reducidos: solo photoUrl y phoneNumber + info del usuario desde UserAuth
 */
public class StandardProfileResponseDto {

    private Long id; // ID del perfil
    private Long userId; // ID del usuario autenticado
    private String email; // Del UserAuth
    private String name; // Del UserAuth
    private String photoUrl;
    private String phoneNumber;

    // ============ CONSTRUCTORS ============
    public StandardProfileResponseDto() {
    }

    public StandardProfileResponseDto(Long id, Long userId, String email, String name, 
            String photoUrl, String phoneNumber) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
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
}
