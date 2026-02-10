package ups.edu.ec.proyect_backend.availability.dtos;

/**
 * DTO de respuesta para la disponibilidad de un día específico
 */
public class DayAvailabilityResponseDto {
    
    private Long id;
    private String day;
    private TimeSlotDto slots;

    // Constructors
    public DayAvailabilityResponseDto() {
    }

    public DayAvailabilityResponseDto(Long id, String day, TimeSlotDto slots) {
        this.id = id;
        this.day = day;
        this.slots = slots;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
