package ups.edu.ec.proyect_backend.applications.entities;

import jakarta.persistence.*;
import ups.edu.ec.proyect_backend.applications.models.ApplicationStatus;
import ups.edu.ec.proyect_backend.core.Entities.BaseEntity;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;

/**
 * Entidad que representa una solicitud de servicio entre un cliente y un programador
 */
@Entity
@Table(name = "service_applications")
public class ServiceApplicationEntity extends BaseEntity {
    
    // Relación con el cliente (quien solicita)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private UserProfileEntity client;
    
    @Column(name = "client_name", nullable = false, length = 100)
    private String clientName;
    
    // Relación con el programador (quien recibe la solicitud)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "programmer_id", nullable = false)
    private UserProfileEntity programmer;
    
    @Column(name = "programmer_name", length = 100)
    private String programmerName;
    
    // Estado de la solicitud
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status;
    
    // Detalles de la solicitud
    @Column(nullable = false, length = 200)
    private String subject;
    
    @Column(nullable = false, length = 1000)
    private String description;
    
    @Column(length = 50)
    private String budget;
    
    // Agendamiento propuesto (timestamps en milisegundos)
    @Column(name = "scheduled_date", nullable = false)
    private Long scheduledDate;
    
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
    
    @Column(name = "start_time", nullable = false)
    private Long startTime;
    
    @Column(name = "end_time", nullable = false)
    private Long endTime;
    
    // Metadatos (timestamps en milisegundos)
    @Column(name = "created_at", nullable = false)
    private Long createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;
    
    // Respuesta del programador (opcional)
    @Column(name = "meeting_link", length = 500)
    private String meetingLink;
    
    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

    // Constructors
    public ServiceApplicationEntity() {
    }

    // Getters and Setters
    public UserProfileEntity getClient() {
        return client;
    }

    public void setClient(UserProfileEntity client) {
        this.client = client;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public UserProfileEntity getProgrammer() {
        return programmer;
    }

    public void setProgrammer(UserProfileEntity programmer) {
        this.programmer = programmer;
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
