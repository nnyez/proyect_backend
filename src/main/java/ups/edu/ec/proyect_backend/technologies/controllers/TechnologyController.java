package ups.edu.ec.proyect_backend.technologies.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ups.edu.ec.proyect_backend.technologies.dtos.CreateTechnologyDto;
import ups.edu.ec.proyect_backend.technologies.dtos.TechnologyResponseDto;
import ups.edu.ec.proyect_backend.technologies.dtos.UpdateTechnologyDto;
import ups.edu.ec.proyect_backend.technologies.services.TechnologyService;

/**
 * REST Controller para operaciones CRUD de tecnologias
 */
@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    /**
     * POST /api/technologies
     * Crear una nueva tecnologia
     */
    @PostMapping
    public ResponseEntity<TechnologyResponseDto> createTechnology(
            @Valid @RequestBody CreateTechnologyDto createDto) {
        TechnologyResponseDto response = technologyService.createTechnology(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/technologies/{id}
     * Obtener una tecnologia por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TechnologyResponseDto> getTechnologyById(@PathVariable Long id) {
        TechnologyResponseDto response = technologyService.getTechnologyById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/technologies
     * Obtener todas las tecnologias
     */
    @GetMapping
    public ResponseEntity<List<TechnologyResponseDto>> getAllTechnologies() {
        List<TechnologyResponseDto> response = technologyService.getAllTechnologies();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/technologies/by-name/{name}
     * Verificar si existe y devolver la tecnologia por nombre
     */
    @GetMapping("/by-name/{name}")
    public ResponseEntity<TechnologyResponseDto> getTechnologyByName(@PathVariable String name) {
        TechnologyResponseDto response = technologyService.getTechnologyByName(name);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/technologies/{id}
     * Actualizar completamente una tecnologia
     */
    @PutMapping("/{id}")
    public ResponseEntity<TechnologyResponseDto> updateTechnology(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTechnologyDto updateDto) {
        TechnologyResponseDto response = technologyService.updateTechnology(id, updateDto);
        return ResponseEntity.ok(response);
    }
}
