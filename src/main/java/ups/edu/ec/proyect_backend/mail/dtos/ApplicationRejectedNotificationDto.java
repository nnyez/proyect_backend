package ups.edu.ec.proyect_backend.mail.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para enviar notificación de solicitud rechazada al cliente
 */
public class ApplicationRejectedNotificationDto {

    @Email(message = "El email del cliente debe ser válido")
    @NotBlank(message = "El email del cliente no puede estar vacío")
    private String clientEmail;

    @NotBlank(message = "El nombre del programador no puede estar vacío")
    private String programmerName;

    @NotBlank(message = "El asunto no puede estar vacío")
    private String subject;

    private String reason;

    // Constructores
    public ApplicationRejectedNotificationDto() {
    }

    public ApplicationRejectedNotificationDto(String clientEmail, String programmerName, String subject, String reason) {
        this.clientEmail = clientEmail;
        this.programmerName = programmerName;
        this.subject = subject;
        this.reason = reason;
    }

    public ApplicationRejectedNotificationDto(String clientEmail, String programmerName, String subject) {
        this.clientEmail = clientEmail;
        this.programmerName = programmerName;
        this.subject = subject;
    }

    // Getters y Setters
    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getProgrammerName() {
        return programmerName;
    }

    public void setProgrammerName(String programmerName) {
        this.programmerName = programmerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
