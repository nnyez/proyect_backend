package ups.edu.ec.proyect_backend.availability.dtos;

import java.util.List;

/**
 * DTO de respuesta para la disponibilidad de un usuario
 */
public class AvailabilityResponseDto {
    
    private Long uid;
    private List<DayAvailabilityResponseDto> weeklySchedule;

    // Constructors
    public AvailabilityResponseDto() {
    }

    public AvailabilityResponseDto(Long uid, List<DayAvailabilityResponseDto> weeklySchedule) {
        this.uid = uid;
        this.weeklySchedule = weeklySchedule;
    }

    // Getters and Setters
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<DayAvailabilityResponseDto> getWeeklySchedule() {
        return weeklySchedule;
    }

    public void setWeeklySchedule(List<DayAvailabilityResponseDto> weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }
}
