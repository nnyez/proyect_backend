package ups.edu.ec.proyect_backend.technologies.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ups.edu.ec.proyect_backend.core.Entities.BaseEntity;
import ups.edu.ec.proyect_backend.projects.entities.ProjectEntity;

@Entity
@Table(name = "technologies")
public class TechnologyEntity extends BaseEntity {
    @Column(nullable = false, length = 100)
    String technology;

    @ManyToMany(mappedBy = "technologies")
    Set<ProjectEntity> projects = new HashSet<>();

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }


    
}
