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

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="project_id")
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
}
