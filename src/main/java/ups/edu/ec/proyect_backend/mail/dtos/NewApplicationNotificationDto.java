package ups.edu.ec.proyect_backend.mail.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para enviar notificación de nueva solicitud de servicio al programador
 */
public class NewApplicationNotificationDto {

    @Email(message = "El email del programador debe ser válido")
    @NotBlank(message = "El email del programador no puede estar vacío")
    private String programmerEmail;

    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    private String clientName;

    @NotBlank(message = "El asunto no puede estar vacío")
    private String subject;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotBlank(message = "La fecha programada no puede estar vacía")
    private String scheduledDate;

    // Constructores
    public NewApplicationNotificationDto() {
    }

    public NewApplicationNotificationDto(String programmerEmail, String clientName, String subject, String description, String scheduledDate) {
        this.programmerEmail = programmerEmail;
        this.clientName = clientName;
        this.subject = subject;
        this.description = description;
        this.scheduledDate = scheduledDate;
    }

    // Getters y Setters
    public String getProgrammerEmail() {
        return programmerEmail;
    }

    public void setProgrammerEmail(String programmerEmail) {
        this.programmerEmail = programmerEmail;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}
