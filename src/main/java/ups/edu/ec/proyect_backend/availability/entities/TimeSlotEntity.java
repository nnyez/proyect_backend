package ups.edu.ec.proyect_backend.availability.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Entidad embebible que representa un slot de tiempo (horario de inicio y fin)
 * Formato: "HH:mm" ej: "09:00"
 */
@Embeddable
public class TimeSlotEntity {
    
    @Column(name = "start_time", nullable = false, length = 5)
    private String start;
    
    @Column(name = "end_time", nullable = false, length = 5)
    private String end;

    // Constructors
    public TimeSlotEntity() {
    }

    public TimeSlotEntity(String start, String end) {
        this.start = start;
        this.end = end;
    }

    // Getters and Setters
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
