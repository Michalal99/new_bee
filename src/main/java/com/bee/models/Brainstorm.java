package com.bee.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="brainstorms")
public class Brainstorm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @NotBlank
    private String description;

    @NotBlank
    @Size(max=255)
    private String title;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="brainstorm", orphanRemoval = true)
    private List<Idea> idea;

    public Brainstorm() {
    }

    public Brainstorm(Long id, String description, String title) {
        this.id = id;
        this.description = description;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
