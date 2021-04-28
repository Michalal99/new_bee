package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    @Size(max=50)
    private String user_id;

    @NotBlank
    private Long idea_id;

    @NotBlank
    private Long grade;

    public Grade(Long id, String user_id, Long idea_id, Long grade) {
        this.id = id;
        this.user_id = user_id;
        this.idea_id = idea_id;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Long getIdea_id() {
        return idea_id;
    }

    public void setIdea_id(Long idea_id) {
        this.idea_id = idea_id;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }
}
