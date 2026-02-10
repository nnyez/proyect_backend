package ups.edu.ec.proyect_backend.projects.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import ups.edu.ec.proyect_backend.projects.entities.ProjectEntity;

/**
 * DTO de respuesta para proyectos
 */
public class ProjectResponseDto {
    
    private Long id;
    private String project;
    private String description;
    private String projectUrl;
    private String imageUrl;
    private Long ownerId;
    private String ownerName;
    private Set<TechnologyDto> technologies;

    // Constructor vacío
    public ProjectResponseDto() {
    }

    // Constructor desde entidad
    public ProjectResponseDto(ProjectEntity entity) {
        this.id = entity.getId();
        this.project = entity.getProject();
        this.description = entity.getDescription();
        this.projectUrl = entity.getProjectUrl();
        this.imageUrl = entity.getImageUrl();
        
        if (entity.getOwner() != null) {
            this.ownerId = entity.getOwner().getId();
            this.ownerName = entity.getOwner().getAuth() != null ? 
                entity.getOwner().getAuth().getName() : null;
        }
        
        if (entity.getTechnologies() != null) {
            this.technologies = entity.getTechnologies().stream()
                .map(tech -> new TechnologyDto(tech.getId(), tech.getTechnology()))
                .collect(Collectors.toSet());
        }
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Set<TechnologyDto> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<TechnologyDto> technologies) {
        this.technologies = technologies;
    }

    // Clase interna para tecnologías
    public static class TechnologyDto {
        private Long id;
        private String name;

        public TechnologyDto() {
        }

        public TechnologyDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
