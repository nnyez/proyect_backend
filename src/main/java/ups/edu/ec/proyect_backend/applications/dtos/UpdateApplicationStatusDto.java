package ups.edu.ec.proyect_backend.applications.dtos;

import jakarta.validation.constraints.Size;
import ups.edu.ec.proyect_backend.applications.models.ApplicationStatus;

/**
 * DTO para actualizar el estado de una solicitud de servicio
 */
public class UpdateApplicationStatusDto {
    
    private ApplicationStatus status;
    
    @Size(max = 500, message = "El enlace de reunión no puede exceder 500 caracteres")
    private String meetingLink;
    
    @Size(max = 500, message = "El motivo de rechazo no puede exceder 500 caracteres")
    private String rejectionReason;

    // Constructors
    public UpdateApplicationStatusDto() {
    }

    public UpdateApplicationStatusDto(ApplicationStatus status, String meetingLink, String rejectionReason) {
        this.status = status;
        this.meetingLink = meetingLink;
        this.rejectionReason = rejectionReason;
    }

    // Getters and Setters
    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
