package ups.edu.ec.proyect_backend.technologies.dtos;

import ups.edu.ec.proyect_backend.technologies.entities.TechnologyEntity;

/**
 * DTO de respuesta para tecnologias
 */
public class TechnologyResponseDto {

    private Long id;
    private String technology;

    public TechnologyResponseDto() {
    }

    public TechnologyResponseDto(TechnologyEntity entity) {
        this.id = entity.getId();
        this.technology = entity.getTechnology();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
