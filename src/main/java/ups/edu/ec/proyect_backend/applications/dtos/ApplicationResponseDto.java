package ups.edu.ec.proyect_backend.applications.dtos;

import ups.edu.ec.proyect_backend.applications.models.ApplicationStatus;

/**
 * DTO de respuesta para una solicitud de servicio
 */
public class ApplicationResponseDto {
    
    private Long id;
    
    // Relación
    private Long clientUid;
    private String clientName;
    private Long programmerUid;
    private String programmerName;
    
    // Estado
    private ApplicationStatus status;
    
    // Detalles de la solicitud
    private String subject;
    private String description;
    private String budget;
    
    // Agendamiento
    private Long scheduledDate;
    private Integer durationMinutes;
    private Long startTime;
    private Long endTime;
    
    // Metadatos
    private Long createdAt;
    private Long updatedAt;
    
    // Respuesta
    private String meetingLink;
    private String rejectionReason;

    // Constructors
    public ApplicationResponseDto() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientUid() {
        return clientUid;
    }

    public void setClientUid(Long clientUid) {
        this.clientUid = clientUid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getProgrammerUid() {
        return programmerUid;
    }

    public void setProgrammerUid(Long programmerUid) {
        this.programmerUid = programmerUid;
    }

    public String getProgrammerName() {
        return programmerName;
    }

    public void setProgrammerName(String programmerName) {
        this.programmerName = programmerName;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
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
