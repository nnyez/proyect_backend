package ups.edu.ec.proyect_backend.users.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ups.edu.ec.proyect_backend.users.dtos.ProfileExistsResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.UserProfileResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.CreateDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.DeveloperProfileResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.PatchDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.UpdateDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.CreateStandardProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.PatchStandardProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.StandardProfileResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.UpdateStandardProfileDto;
import ups.edu.ec.proyect_backend.users.services.UserProfileService;

/**
 * REST Controller para operaciones CRUD de perfiles de usuario
 * 
 * Soporta dos tipos de perfiles:
 * - STANDARD: Perfiles básicos con solo photoUrl y phoneNumber
 * - DEVELOPER/ADMIN: Perfiles completos con skills, experiencia, título y biografía
 */
@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    // ============ GENERAL PROFILE ENDPOINTS ============

    /**
     * GET /api/profiles/{id}
     * Obtener el perfil de cualquier usuario por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> getProfileById(@PathVariable Long id) {
        UserProfileResponseDto response = userProfileService.getProfileById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/profiles/{id}/exists
     * Verificar si un perfil existe por su ID
     */
    @GetMapping("/{id}/exists") 
    public ResponseEntity<ProfileExistsResponseDto> checkProfileExists(@PathVariable Long id) {
        ProfileExistsResponseDto response = userProfileService.checkProfileExists(id);
        return ResponseEntity.ok(response);
    }

    // ============ STANDARD PROFILE ENDPOINTS ============

    /**
     * POST /api/profiles/standard
     * Crear un perfil STANDARD
     */
    @PostMapping("/standard")
    public ResponseEntity<StandardProfileResponseDto> createStandardProfile(
            @RequestParam Long userId,
            @Valid @RequestBody CreateStandardProfileDto createDto) {
        StandardProfileResponseDto response = userProfileService.createStandardProfile(userId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/profiles/standard
     * Obtener perfil STANDARD de un usuario
     */
    @GetMapping("/standard/{userId}")
    public ResponseEntity<StandardProfileResponseDto> getStandardProfile(@PathVariable Long userId) {
        StandardProfileResponseDto response = userProfileService.getStandardProfile(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/profiles/standard
     * Actualizar completamente un perfil STANDARD
     */
    @PutMapping("/standard/{userId}")
    public ResponseEntity<StandardProfileResponseDto> updateStandardProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateStandardProfileDto updateDto) {
        StandardProfileResponseDto response = userProfileService.updateStandardProfile(userId, updateDto);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/profiles/standard
     * Actualizar parcialmente un perfil STANDARD
     */
    @PatchMapping("/standard")
    public ResponseEntity<StandardProfileResponseDto> patchStandardProfile(
            @RequestParam Long userId,
            @Valid @RequestBody PatchStandardProfileDto patchDto) {
        StandardProfileResponseDto response = userProfileService.patchStandardProfile(userId, patchDto);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/profiles/standard
     * Eliminar un perfil STANDARD
     */
    @DeleteMapping("/standard")
    public ResponseEntity<Void> deleteStandardProfile(@RequestParam Long userId) {
        userProfileService.deleteStandardProfile(userId);
        return ResponseEntity.noContent().build();
    }

    // ============ DEVELOPER PROFILE ENDPOINTS ============

    /**
     * POST /api/profiles/developer
     * Crear un perfil DEVELOPER/ADMIN
     */
    @PostMapping("/developer")
    public ResponseEntity<DeveloperProfileResponseDto> createDeveloperProfile(
            @RequestParam Long userId,
            @Valid @RequestBody CreateDeveloperProfileDto createDto) {
        DeveloperProfileResponseDto response = userProfileService.createDeveloperProfile(userId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/profiles/developer
     * Obtener perfil DEVELOPER/ADMIN de un usuario
     */
    @GetMapping("/developer/{userId}")
    public ResponseEntity<DeveloperProfileResponseDto> getDeveloperProfile(@PathVariable Long userId) {
        DeveloperProfileResponseDto response = userProfileService.getDeveloperProfile(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/profiles/developer
     * Actualizar completamente un perfil DEVELOPER/ADMIN
     */
    @PutMapping("/developer")
    public ResponseEntity<DeveloperProfileResponseDto> updateDeveloperProfile(
            @RequestParam Long userId,
            @Valid @RequestBody UpdateDeveloperProfileDto updateDto) {
        DeveloperProfileResponseDto response = userProfileService.updateDeveloperProfile(userId, updateDto);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/profiles/developer
     * Actualizar parcialmente un perfil DEVELOPER/ADMIN
     */
    @PatchMapping("/developer")
    public ResponseEntity<DeveloperProfileResponseDto> patchDeveloperProfile(
            @RequestParam Long userId,
            @Valid @RequestBody PatchDeveloperProfileDto patchDto) {
        DeveloperProfileResponseDto response = userProfileService.patchDeveloperProfile(userId, patchDto);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/profiles/developer
     * Eliminar un perfil DEVELOPER/ADMIN
     */
    @DeleteMapping("/developer")
    public ResponseEntity<Void> deleteDeveloperProfile(@RequestParam Long userId) {
        userProfileService.deleteDeveloperProfile(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/profiles/developers
     * Obtener todos los perfiles de desarrolladores/programadores
     */
    @GetMapping("/developers")
    public ResponseEntity<List<DeveloperProfileResponseDto>> getAllDevelopers() {
        List<DeveloperProfileResponseDto> response = userProfileService.getAllDeveloperProfiles();
        return ResponseEntity.ok(response);
    }
}
