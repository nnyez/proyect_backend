package ups.edu.ec.proyect_backend.applications.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ups.edu.ec.proyect_backend.applications.dtos.ApplicationResponseDto;
import ups.edu.ec.proyect_backend.applications.dtos.CreateApplicationDto;
import ups.edu.ec.proyect_backend.applications.dtos.UpdateApplicationStatusDto;
import ups.edu.ec.proyect_backend.applications.models.ApplicationStatus;
import ups.edu.ec.proyect_backend.applications.services.ServiceApplicationService;

import java.util.List;

/**
 * REST Controller para gestionar solicitudes de servicio
 */
@RestController
@RequestMapping("/api/applications")
public class ServiceApplicationController {

    private final ServiceApplicationService applicationService;

    public ServiceApplicationController(ServiceApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * POST /api/applications/client/{clientId}
     * Crear una nueva solicitud de servicio como cliente
     */
    @PostMapping("/client/{clientId}")
    public ResponseEntity<ApplicationResponseDto> createApplication(
            @PathVariable Long clientId,
            @Valid @RequestBody CreateApplicationDto createDto) {
        ApplicationResponseDto response = applicationService.createApplication(clientId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/applications/{id}
     * Obtener una solicitud por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDto> getApplication(@PathVariable Long id) {
        ApplicationResponseDto response = applicationService.getApplicationById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/applications/client/{clientId}
     * Obtener todas las solicitudes creadas por un cliente
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ApplicationResponseDto>> getApplicationsByClient(
            @PathVariable Long clientId,
            @RequestParam(required = false) ApplicationStatus status) {
        List<ApplicationResponseDto> response;
        
        if (status != null) {
            response = applicationService.getApplicationsByClientAndStatus(clientId, status);
        } else {
            response = applicationService.getApplicationsByClient(clientId);
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/applications/programmer/{programmerId}
     * Obtener todas las solicitudes dirigidas a un programador
     */
    @GetMapping("/programmer/{programmerId}")
    public ResponseEntity<List<ApplicationResponseDto>> getApplicationsByProgrammer(
            @PathVariable Long programmerId,
            @RequestParam(required = false) ApplicationStatus status) {
        List<ApplicationResponseDto> response;
        
        if (status != null) {
            response = applicationService.getApplicationsByProgrammerAndStatus(programmerId, status);
        } else {
            response = applicationService.getApplicationsByProgrammer(programmerId);
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/applications/{id}/status
     * Actualizar el estado de una solicitud (aceptar/rechazar/completar/cancelar)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponseDto> updateApplicationStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateApplicationStatusDto updateDto) {
        ApplicationResponseDto response = applicationService.updateApplicationStatus(id, updateDto);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/applications/{id}
     * Eliminar una solicitud
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
