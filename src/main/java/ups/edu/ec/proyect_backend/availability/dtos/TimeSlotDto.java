package ups.edu.ec.proyect_backend.availability.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para representar un slot de tiempo
 */
public class TimeSlotDto {
    
    @NotBlank(message = "La hora de inicio es obligatoria")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Formato de hora inválido. Use HH:mm")
    private String start;
    
    @NotBlank(message = "La hora de fin es obligatoria")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Formato de hora inválido. Use HH:mm")
    private String end;

    // Constructors
    public TimeSlotDto() {
    }

    public TimeSlotDto(String start, String end) {
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
