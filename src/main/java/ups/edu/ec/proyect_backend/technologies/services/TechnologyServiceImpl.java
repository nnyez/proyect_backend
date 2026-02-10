package ups.edu.ec.proyect_backend.technologies.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ups.edu.ec.proyect_backend.core.exceptions.domain.NotFoundException;
import ups.edu.ec.proyect_backend.technologies.dtos.CreateTechnologyDto;
import ups.edu.ec.proyect_backend.technologies.dtos.TechnologyResponseDto;
import ups.edu.ec.proyect_backend.technologies.dtos.UpdateTechnologyDto;
import ups.edu.ec.proyect_backend.technologies.entities.TechnologyEntity;
import ups.edu.ec.proyect_backend.technologies.repositories.TechnologyRepository;

/**
 * Implementacion del servicio de tecnologias
 */
@Service
@Transactional
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;

    public TechnologyServiceImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public TechnologyResponseDto createTechnology(CreateTechnologyDto createDto) {
        TechnologyEntity technology = new TechnologyEntity();
        technology.setTechnology(createDto.getTechnology());

        TechnologyEntity savedTechnology = technologyRepository.save(technology);
        return new TechnologyResponseDto(savedTechnology);
    }

    @Override
    @Transactional(readOnly = true)
    public TechnologyResponseDto getTechnologyById(Long technologyId) {
        TechnologyEntity technology = technologyRepository.findById(technologyId)
                .orElseThrow(() -> new NotFoundException("Tecnologia no encontrada con ID: " + technologyId));
        return new TechnologyResponseDto(technology);
    }

    @Override
    @Transactional(readOnly = true)
    public TechnologyResponseDto getTechnologyByName(String technologyName) {
        TechnologyEntity technology = technologyRepository.findByTechnologyIgnoreCase(technologyName)
                .orElseThrow(() -> new NotFoundException("Tecnologia no encontrada con nombre: " + technologyName));
        return new TechnologyResponseDto(technology);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TechnologyResponseDto> getAllTechnologies() {
        return technologyRepository.findAll().stream()
                .map(TechnologyResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public TechnologyResponseDto updateTechnology(Long technologyId, UpdateTechnologyDto updateDto) {
        TechnologyEntity technology = technologyRepository.findById(technologyId)
                .orElseThrow(() -> new NotFoundException("Tecnologia no encontrada con ID: " + technologyId));

        technology.setTechnology(updateDto.getTechnology());

        TechnologyEntity updatedTechnology = technologyRepository.save(technology);
        return new TechnologyResponseDto(updatedTechnology);
    }
}
