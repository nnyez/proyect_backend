package ups.edu.ec.proyect_backend.projects.services;

import ups.edu.ec.proyect_backend.projects.dtos.CreateProjectDto;
import ups.edu.ec.proyect_backend.projects.dtos.PatchProjectDto;
import ups.edu.ec.proyect_backend.projects.dtos.ProjectResponseDto;
import ups.edu.ec.proyect_backend.projects.dtos.UpdateProjectDto;

import java.util.List;

/**
 * Interfaz de servicio para operaciones CRUD de proyectos
 */
public interface ProjectService {

    /**
     * Crea un nuevo proyecto
     * @param userId ID del usuario propietario
     * @param createDto DTO con datos del proyecto
     * @return Respuesta del proyecto creado
     */
    ProjectResponseDto createProject(Long userId, CreateProjectDto createDto);

    /**
     * Actualiza completamente un proyecto (PUT)
     * @param projectId ID del proyecto
     * @param updateDto DTO con nuevos datos
     * @return Respuesta del proyecto actualizado
     */
    ProjectResponseDto updateProject(Long projectId, UpdateProjectDto updateDto);

    /**
     * Actualiza parcialmente un proyecto (PATCH)
     * @param projectId ID del proyecto
     * @param patchDto DTO con datos a actualizar
     * @return Respuesta del proyecto actualizado
     */
    ProjectResponseDto patchProject(Long projectId, PatchProjectDto patchDto);

    /**
     * Obtiene un proyecto por su ID
     * @param projectId ID del proyecto
     * @return Respuesta del proyecto
     */
    ProjectResponseDto getProjectById(Long projectId);

    /**
     * Obtiene todos los proyectos
     * @return Lista de proyectos
     */
    List<ProjectResponseDto> getAllProjects();

    /**
     * Obtiene proyectos por ID del propietario
     * @param userId ID del usuario propietario
     * @return Lista de proyectos del usuario
     */
    List<ProjectResponseDto> getProjectsByUserId(Long userId);

    /**
     * Elimina un proyecto
     * @param projectId ID del proyecto
     */
    void deleteProject(Long projectId);
}
