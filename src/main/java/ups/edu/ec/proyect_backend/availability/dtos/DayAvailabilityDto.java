package ups.edu.ec.proyect_backend.availability.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para representar la disponibilidad de un día
 */
public class DayAvailabilityDto {
    
    @NotBlank(message = "El día es obligatorio")
    @Pattern(regexp = "^(monday|tuesday|wednesday|thursday|friday|saturday|sunday)$", 
             message = "Día inválido. Use: monday, tuesday, wednesday, thursday, friday, saturday, sunday")
    private String day;
    
    @NotNull(message = "Los slots de tiempo son obligatorios")
    @Valid
    private TimeSlotDto slots;

    // Constructors
    public DayAvailabilityDto() {
    }

    public DayAvailabilityDto(String day, TimeSlotDto slots) {
        this.day = day;
        this.slots = slots;
    }

    // Getters and Setters
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public TimeSlotDto getSlots() {
        return slots;
    }

    public void setSlots(TimeSlotDto slots) {
        this.slots = slots;
    }
}
