package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="kanban")
public class Kanban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    private Long projects_id;

    public Kanban(Long id, Long projects_id) {
        this.id = id;
        this.projects_id = projects_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjects_id() {
        return projects_id;
    }

    public void setProjects_id(Long projects_id) {
        this.projects_id = projects_id;
    }
}
