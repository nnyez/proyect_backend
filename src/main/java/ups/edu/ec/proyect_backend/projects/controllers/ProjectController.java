package ups.edu.ec.proyect_backend.projects.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import ups.edu.ec.proyect_backend.projects.dtos.CreateProjectDto;
import ups.edu.ec.proyect_backend.projects.dtos.PatchProjectDto;
import ups.edu.ec.proyect_backend.projects.dtos.ProjectResponseDto;
import ups.edu.ec.proyect_backend.projects.dtos.UpdateProjectDto;
import ups.edu.ec.proyect_backend.projects.services.ProjectService;

import java.util.List;

/**
 * REST Controller para operaciones CRUD de proyectos
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * POST /api/projects
     * Crear un nuevo proyecto
     */
    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(
            @RequestParam Long userId,
            @Valid @RequestBody CreateProjectDto createDto) {
        ProjectResponseDto response = projectService.createProject(userId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /api/projects/{id}
     * Actualizar completamente un proyecto
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectDto updateDto) {
        ProjectResponseDto response = projectService.updateProject(id, updateDto);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/projects/{id}
     * Actualizar parcialmente un proyecto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> patchProject(
            @PathVariable Long id,
            @Valid @RequestBody PatchProjectDto patchDto) {
        ProjectResponseDto response = projectService.patchProject(id, patchDto);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/projects/{id}
     * Obtener un proyecto por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long id) {
        ProjectResponseDto response = projectService.getProjectById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/projects
     * Obtener todos los proyectos
     */
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        List<ProjectResponseDto> response = projectService.getAllProjects();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/projects/user/{userId}
     * Obtener proyectos de un usuario específico
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByUserId(@PathVariable Long userId) {
        List<ProjectResponseDto> response = projectService.getProjectsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/projects/{id}
     * Eliminar un proyecto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
