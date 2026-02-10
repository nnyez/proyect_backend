package ups.edu.ec.proyect_backend.technologies.services;

import java.util.List;

import ups.edu.ec.proyect_backend.technologies.dtos.CreateTechnologyDto;
import ups.edu.ec.proyect_backend.technologies.dtos.TechnologyResponseDto;
import ups.edu.ec.proyect_backend.technologies.dtos.UpdateTechnologyDto;

/**
 * Interfaz de servicio para operaciones CRUD de tecnologias
 */
public interface TechnologyService {

    /**
     * Crea una nueva tecnologia
     */
    TechnologyResponseDto createTechnology(CreateTechnologyDto createDto);

    /**
     * Obtiene una tecnologia por ID
     */
    TechnologyResponseDto getTechnologyById(Long technologyId);

    /**
     * Obtiene una tecnologia por nombre
     */
    TechnologyResponseDto getTechnologyByName(String technology);

    /**
     * Obtiene todas las tecnologias
     */
    List<TechnologyResponseDto> getAllTechnologies();

    /**
     * Actualiza completamente una tecnologia
     */
    TechnologyResponseDto updateTechnology(Long technologyId, UpdateTechnologyDto updateDto);
}
