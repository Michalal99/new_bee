package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    private Long brainstorms_id;

    @NotBlank
    @Size(max=255)
    private String title;

    @NotBlank
    private String text;

    public Idea(Long id, Long brainstorms_id, String title, String text) {
        this.id = id;
        this.brainstorms_id = brainstorms_id;
        this.title = title;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrainstorms_id() {
        return brainstorms_id;
    }

    public void setBrainstorms_id(Long brainstorms_id) {
        this.brainstorms_id = brainstorms_id;
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
}
