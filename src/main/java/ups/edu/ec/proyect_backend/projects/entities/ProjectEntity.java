package ups.edu.ec.proyect_backend.projects.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ups.edu.ec.proyect_backend.core.Entities.BaseEntity;
import ups.edu.ec.proyect_backend.technologies.entities.TechnologyEntity;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;

@Entity
@Table(name = "Projects")
public class ProjectEntity extends BaseEntity {
    @Column(nullable = false, length = 100)
    String project;
    @Column(nullable = false, length = 150)
    String description;
    @Column()
    String projectUrl;
    @Column()
    String imageUrl;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserProfileEntity owner;    

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_technologies", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "technology_id"))
    Set<TechnologyEntity> technologies = new HashSet<>();

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

    public UserProfileEntity getOwner() {
        return owner;
    }

    public void setOwner(UserProfileEntity owner) {
        this.owner = owner;
    }

    public Set<TechnologyEntity> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<TechnologyEntity> technologies) {
        this.technologies = technologies;
    }
}
