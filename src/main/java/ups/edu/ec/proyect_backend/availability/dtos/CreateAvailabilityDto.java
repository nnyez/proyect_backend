package ups.edu.ec.proyect_backend.availability.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * DTO para crear/actualizar la configuración de disponibilidad de un usuario
 */
public class CreateAvailabilityDto {
    
    @NotEmpty(message = "Debe proporcionar al menos un día de disponibilidad")
    @Valid
    private List<DayAvailabilityDto> weeklySchedule;

    // Constructors
    public CreateAvailabilityDto() {
    }

    public CreateAvailabilityDto(List<DayAvailabilityDto> weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    // Getters and Setters
    public List<DayAvailabilityDto> getWeeklySchedule() {
        return weeklySchedule;
    }

    public void setWeeklySchedule(List<DayAvailabilityDto> weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }
}
