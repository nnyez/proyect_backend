package ups.edu.ec.proyect_backend.technologies.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear una tecnologia
 */
public class CreateTechnologyDto {

    @NotBlank(message = "El nombre de la tecnologia es obligatorio")
    @Size(max = 100, message = "El nombre de la tecnologia no puede exceder 100 caracteres")
    private String technology;

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
