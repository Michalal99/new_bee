package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="kanbans")
public class Kanban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    private Long projects_id;

    @OneToMany(mappedBy="kanban")
    private Set<Kanban_done> kanban_done;

    @OneToMany(mappedBy="kanban")
    private Set<Kanban_inprogress> kanban_inprogress;

    @OneToMany(mappedBy="kanban")
    private Set<Kanban_todo> kanban_todo;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="project_id")
    private Project project;

    public Kanban() {
    }

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
