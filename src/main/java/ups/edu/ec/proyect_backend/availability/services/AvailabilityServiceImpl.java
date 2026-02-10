package ups.edu.ec.proyect_backend.availability.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ups.edu.ec.proyect_backend.availability.dtos.*;
import ups.edu.ec.proyect_backend.availability.entities.DayAvailabilityEntity;
import ups.edu.ec.proyect_backend.availability.entities.TimeSlotEntity;
import ups.edu.ec.proyect_backend.availability.repositories.DayAvailabilityRepository;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;
import ups.edu.ec.proyect_backend.users.repositories.UserProfileRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de disponibilidad
 */
@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private final DayAvailabilityRepository availabilityRepository;
    private final UserProfileRepository userRepository;

    public AvailabilityServiceImpl(DayAvailabilityRepository availabilityRepository,
                                   UserProfileRepository userRepository) {
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AvailabilityResponseDto createOrUpdateAvailability(Long userId, CreateAvailabilityDto createDto) {
        // Verificar que el usuario existe
        UserProfileEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        // Eliminar disponibilidades anteriores del usuario
        availabilityRepository.deleteByUserId(userId);

        // Crear nuevas disponibilidades
        List<DayAvailabilityEntity> entities = createDto.getWeeklySchedule().stream()
                .map(dayDto -> {
                    TimeSlotEntity timeSlot = new TimeSlotEntity(
                            dayDto.getSlots().getStart(),
                            dayDto.getSlots().getEnd()
                    );
                    return new DayAvailabilityEntity(dayDto.getDay(), timeSlot, user);
                })
                .collect(Collectors.toList());

        // Guardar todas las disponibilidades
        List<DayAvailabilityEntity> saved = availabilityRepository.saveAll(entities);

        // Mapear a DTO de respuesta
        return mapToResponseDto(userId, saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AvailabilityResponseDto getAvailabilityByUserId(Long userId) {
        // Verificar que el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + userId);
        }

        List<DayAvailabilityEntity> entities = availabilityRepository.findByUserId(userId);
        
        return mapToResponseDto(userId, entities);
    }

    @Override
    @Transactional
    public void deleteAvailability(Long userId) {
        // Verificar que el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + userId);
        }

        availabilityRepository.deleteByUserId(userId);
    }

    /**
     * Mapea entidades a DTO de respuesta
     */
    private AvailabilityResponseDto mapToResponseDto(Long userId, List<DayAvailabilityEntity> entities) {
        List<DayAvailabilityResponseDto> schedule = entities.stream()
                .map(entity -> new DayAvailabilityResponseDto(
                        entity.getId(),
                        entity.getDay(),
                        new TimeSlotDto(
                                entity.getSlots().getStart(),
                                entity.getSlots().getEnd()
                        )
                ))
                .collect(Collectors.toList());

        return new AvailabilityResponseDto(userId, schedule);
    }
}
