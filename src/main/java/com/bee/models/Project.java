package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id", nullable = false)
    private Team team;

    @NotBlank
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="project", orphanRemoval = true)
    private Set<Meeting> meeting;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="project", orphanRemoval = true)
    private Set<Brainstorm> brainstorm;

    @OneToMany(mappedBy="project")
    private List<Comment> comment;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy="project", orphanRemoval = true)
    private Kanban kanban;

    public Project() {
    }

    public Project(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
