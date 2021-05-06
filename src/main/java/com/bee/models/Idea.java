package com.bee.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name="ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="brainstorm_id")
    private Brainstorm brainstorm;

    @NotBlank
    @Size(max=255)
    private String title;

    @NotBlank
    private String text;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="idea", orphanRemoval = true)
    private Set<Grade> grade;

    public Idea() {
    }

    public Idea(Long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Brainstorm getBrainstorm() {
        return brainstorm;
    }

    public void setBrainstorm(Brainstorm brainstorm) {
        this.brainstorm = brainstorm;
    }

    public Set<Grade> getGrade() {
        return grade;
    }

    public void setGrade(Set<Grade> grade) {
        this.grade = grade;
    }
}
