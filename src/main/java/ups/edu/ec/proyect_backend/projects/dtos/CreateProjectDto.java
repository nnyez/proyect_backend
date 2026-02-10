package ups.edu.ec.proyect_backend.projects.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

/**
 * DTO para crear un proyecto
 */
public class CreateProjectDto {
    
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 100, message = "El nombre del proyecto no puede exceder 100 caracteres")
    private String project;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 150, message = "La descripción no puede exceder 150 caracteres")
    private String description;
    
    @Size(max = 255, message = "La URL del proyecto no puede exceder 255 caracteres")
    private String projectUrl;
    
    @Size(max = 255, message = "La URL de la imagen no puede exceder 255 caracteres")
    private String imageUrl;
    
    private Set<Long> technologyIds;

    // Getters and Setters

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Long> getTechnologyIds() {
        return technologyIds;
    }

    public void setTechnologyIds(Set<Long> technologyIds) {
        this.technologyIds = technologyIds;
    }
}
