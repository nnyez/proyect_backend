package ups.edu.ec.proyect_backend.users.dtos.standard;

import jakarta.validation.constraints.Size;

/**
 * DTO para actualización parcial (PATCH) de perfil de usuario STANDARD
 * Campos reducidos: solo photoUrl y phoneNumber
 * Todos los campos son opcionales para PATCH
 */
public class PatchStandardProfileDto {

    @Size(max = 500, message = "La URL de foto no puede exceder 500 caracteres")
    private String photoUrl;

    @Size(max = 100, message = "El número de teléfono no puede exceder 100 caracteres")
    private String phoneNumber;

    // ============ CONSTRUCTORS ============
    public PatchStandardProfileDto() {
    }

    public PatchStandardProfileDto(String photoUrl, String phoneNumber) {
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
