package ups.edu.ec.proyect_backend.availability.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ups.edu.ec.proyect_backend.availability.dtos.AvailabilityResponseDto;
import ups.edu.ec.proyect_backend.availability.dtos.CreateAvailabilityDto;
import ups.edu.ec.proyect_backend.availability.services.AvailabilityService;

/**
 * REST Controller para gestionar la disponibilidad de usuarios (programadores)
 */
@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    /**
     * POST /api/availability/{userId}
     * Crear o actualizar la configuración de disponibilidad de un usuario
     */
    @PostMapping("/{userId}")
    public ResponseEntity<AvailabilityResponseDto> createOrUpdateAvailability(
            @PathVariable Long userId,
            @Valid @RequestBody CreateAvailabilityDto createDto) {
        AvailabilityResponseDto response = availabilityService.createOrUpdateAvailability(userId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/availability/{userId}
     * Obtener la configuración de disponibilidad de un usuario
     */
    @GetMapping("/{userId}")
    public ResponseEntity<AvailabilityResponseDto> getAvailability(@PathVariable Long userId) {
        AvailabilityResponseDto response = availabilityService.getAvailabilityByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/availability/{userId}
     * Eliminar la configuración de disponibilidad de un usuario
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long userId) {
        availabilityService.deleteAvailability(userId);
        return ResponseEntity.noContent().build();
    }
}
