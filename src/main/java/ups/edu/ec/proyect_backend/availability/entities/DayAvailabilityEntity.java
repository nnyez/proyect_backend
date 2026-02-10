package ups.edu.ec.proyect_backend.availability.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ups.edu.ec.proyect_backend.core.Entities.BaseEntity;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;

/**
 * Entidad que representa la disponibilidad de un día específico
 */
@Entity
@Table(name = "day_availability")
public class DayAvailabilityEntity extends BaseEntity {
    
    @Column(nullable = false, length = 20)
    private String day; // monday, tuesday, wednesday, thursday, friday, saturday, sunday
    
    @Embedded
    private TimeSlotEntity slots;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfileEntity user;

    // Constructors
    public DayAvailabilityEntity() {
    }

    public DayAvailabilityEntity(String day, TimeSlotEntity slots, UserProfileEntity user) {
        this.day = day;
        this.slots = slots;
        this.user = user;
    }

    // Getters and Setters
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public TimeSlotEntity getSlots() {
        return slots;
    }

    public void setSlots(TimeSlotEntity slots) {
        this.slots = slots;
    }

    public UserProfileEntity getUser() {
        return user;
    }

    public void setUser(UserProfileEntity user) {
        this.user = user;
    }
}
