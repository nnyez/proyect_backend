package ups.edu.ec.proyect_backend.applications.dtos;

import jakarta.validation.constraints.*;

/**
 * DTO para crear una nueva solicitud de servicio
 */
public class CreateApplicationDto {
    
    @NotNull(message = "El ID del programador es obligatorio")
    private Long programmerUid;
    
    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 200, message = "El asunto no puede exceder 200 caracteres")
    private String subject;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;
    
    @Size(max = 50, message = "El presupuesto no puede exceder 50 caracteres")
    private String budget;
    
    @NotNull(message = "La fecha programada es obligatoria")
    @Min(value = 0, message = "La fecha programada debe ser un timestamp válido")
    private Long scheduledDate;
    
    @NotNull(message = "La duración en minutos es obligatoria")
    @Min(value = 15, message = "La duración mínima es 15 minutos")
    @Max(value = 480, message = "La duración máxima es 480 minutos (8 horas)")
    private Integer durationMinutes;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    @Min(value = 0, message = "La hora de inicio debe ser un timestamp válido")
    private Long startTime;
    
    @NotNull(message = "La hora de fin es obligatoria")
    @Min(value = 0, message = "La hora de fin debe ser un timestamp válido")
    private Long endTime;

    // Constructors
    public CreateApplicationDto() {
    }

    // Getters and Setters
    public Long getProgrammerUid() {
        return programmerUid;
    }

    public void setProgrammerUid(Long programmerUid) {
        this.programmerUid = programmerUid;
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

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Long getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Long scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
