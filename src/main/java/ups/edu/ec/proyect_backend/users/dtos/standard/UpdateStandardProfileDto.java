package ups.edu.ec.proyect_backend.users.dtos.standard;

import jakarta.validation.constraints.Size;

/**
 * DTO para actualización (PUT) de perfil de usuario STANDARD
 * Campos reducidos: solo photoUrl y phoneNumber
 * Todos los campos son requeridos para PUT
 */
public class UpdateStandardProfileDto {

    @Size(max = 500, message = "La URL de foto no puede exceder 500 caracteres")
    private String photoUrl;

    @Size(max = 100, message = "El número de teléfono no puede exceder 100 caracteres")
    private String phoneNumber;

    // ============ CONSTRUCTORS ============
    public UpdateStandardProfileDto() {
    }

    public UpdateStandardProfileDto(String photoUrl, String phoneNumber) {
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
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
}
