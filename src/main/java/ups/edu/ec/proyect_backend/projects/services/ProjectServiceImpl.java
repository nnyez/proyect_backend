package ups.edu.ec.proyect_backend.projects.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ups.edu.ec.proyect_backend.core.exceptions.domain.NotFoundException;
import ups.edu.ec.proyect_backend.projects.dtos.CreateProjectDto;
import ups.edu.ec.proyect_backend.projects.dtos.PatchProjectDto;
import ups.edu.ec.proyect_backend.projects.dtos.ProjectResponseDto;
import ups.edu.ec.proyect_backend.projects.dtos.UpdateProjectDto;
import ups.edu.ec.proyect_backend.projects.entities.ProjectEntity;
import ups.edu.ec.proyect_backend.projects.repositories.ProjectRepository;
import ups.edu.ec.proyect_backend.technologies.entities.TechnologyEntity;
import ups.edu.ec.proyect_backend.technologies.repositories.TechnologyRepository;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;
import ups.edu.ec.proyect_backend.users.repositories.UserProfileRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de proyectos
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserProfileRepository userProfileRepository;
    private final TechnologyRepository technologyRepository;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            UserProfileRepository userProfileRepository,
            TechnologyRepository technologyRepository) {
        this.projectRepository = projectRepository;
        this.userProfileRepository = userProfileRepository;
        this.technologyRepository = technologyRepository;
    }

    @Override
    public ProjectResponseDto createProject(Long userId, CreateProjectDto createDto) {
        // Buscar el perfil del usuario
        UserProfileEntity owner = userProfileRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + userId));

        // Crear la entidad del proyecto
        ProjectEntity project = new ProjectEntity();
        project.setProject(createDto.getProject());
        project.setDescription(createDto.getDescription());
        project.setProjectUrl(createDto.getProjectUrl());
        project.setImageUrl(createDto.getImageUrl());
        project.setOwner(owner);

        // Asignar tecnologías si se proporcionan
        if (createDto.getTechnologyIds() != null && !createDto.getTechnologyIds().isEmpty()) {
            Set<TechnologyEntity> technologies = loadTechnologies(createDto.getTechnologyIds());
            project.setTechnologies(technologies);
        }

        // Guardar el proyecto
        ProjectEntity savedProject = projectRepository.save(project);

        return new ProjectResponseDto(savedProject);
    }

    @Override
    public ProjectResponseDto updateProject(Long projectId, UpdateProjectDto updateDto) {
        // Buscar el proyecto
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Proyecto no encontrado con ID: " + projectId));

        // Actualizar todos los campos
        project.setProject(updateDto.getProject());
        project.setDescription(updateDto.getDescription());
        project.setProjectUrl(updateDto.getProjectUrl());
        project.setImageUrl(updateDto.getImageUrl());

        // Actualizar tecnologías
        if (updateDto.getTechnologyIds() != null) {
            Set<TechnologyEntity> technologies = loadTechnologies(updateDto.getTechnologyIds());
            project.setTechnologies(technologies);
        } else {
            project.setTechnologies(new HashSet<>());
        }

        // Guardar cambios
        ProjectEntity updatedProject = projectRepository.save(project);

        return new ProjectResponseDto(updatedProject);
    }

    @Override
    public ProjectResponseDto patchProject(Long projectId, PatchProjectDto patchDto) {
        // Buscar el proyecto
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Proyecto no encontrado con ID: " + projectId));

        // Actualizar solo los campos proporcionados
        if (patchDto.getProject() != null) {
            project.setProject(patchDto.getProject());
        }
        if (patchDto.getDescription() != null) {
            project.setDescription(patchDto.getDescription());
        }
        if (patchDto.getProjectUrl() != null) {
            project.setProjectUrl(patchDto.getProjectUrl());
        }
        if (patchDto.getImageUrl() != null) {
            project.setImageUrl(patchDto.getImageUrl());
        }
        if (patchDto.getTechnologyIds() != null) {
            Set<TechnologyEntity> technologies = loadTechnologies(patchDto.getTechnologyIds());
            project.setTechnologies(technologies);
        }

        // Guardar cambios
        ProjectEntity updatedProject = projectRepository.save(project);

        return new ProjectResponseDto(updatedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectById(Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Proyecto no encontrado con ID: " + projectId));

        return new ProjectResponseDto(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getProjectsByUserId(Long userId) {
        // Verificar que el usuario existe
        if (!userProfileRepository.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }

        return projectRepository.findByOwnerId(userId).stream()
                .map(ProjectResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundException("Proyecto no encontrado con ID: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }

    /**
     * Método auxiliar para cargar tecnologías desde sus IDs
     */
    private Set<TechnologyEntity> loadTechnologies(Set<Long> technologyIds) {
        Set<TechnologyEntity> technologies = new HashSet<>();
        for (Long techId : technologyIds) {
            TechnologyEntity tech = technologyRepository.findById(techId)
                    .orElseThrow(() -> new NotFoundException("Tecnología no encontrada con ID: " + techId));
            technologies.add(tech);
        }
        return technologies;
    }
}
