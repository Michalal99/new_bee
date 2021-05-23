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

    @OneToMany(cascade = CascadeType.ALL, mappedBy="kanban", orphanRemoval = true)
    private Set<Kanban_done> kanban_done;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="kanban", orphanRemoval = true)
    private Set<Kanban_inprogress> kanban_inprogress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="kanban", orphanRemoval = true)
    private Set<Kanban_todo> kanban_todo;

    @Column(name="project_id", insertable = false, updatable = false)
    private Long project_id;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="project_id", nullable = false)
    private Project project;

    public Kanban() {
    }

    public Kanban(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Kanban_done> getKanban_done() {
        return kanban_done;
    }

    public void setKanban_done(Set<Kanban_done> kanban_done) {
        this.kanban_done = kanban_done;
    }

    public Set<Kanban_inprogress> getKanban_inprogress() {
        return kanban_inprogress;
    }

    public void setKanban_inprogress(Set<Kanban_inprogress> kanban_inprogress) {
        this.kanban_inprogress = kanban_inprogress;
    }

    public Set<Kanban_todo> getKanban_todo() {
        return kanban_todo;
    }

    public void setKanban_todo(Set<Kanban_todo> kanban_todo) {
        this.kanban_todo = kanban_todo;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }


}
