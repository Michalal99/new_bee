package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name="meetings")
public class Meeting {

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
    private LocalDate start_date;

    @NotBlank
    private int duration_in_minutes;

    public Meeting() {
    }

    public Meeting(Long id, String description, LocalDate start_date, int duration_in_minutes) {
        this.id = id;
        this.description = description;
        this.start_date = start_date;
        this.duration_in_minutes = duration_in_minutes;
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

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public int getDuration_in_minutes() {
        return duration_in_minutes;
    }

    public void setDuration_in_minutes(int duration_in_minutes) {
        this.duration_in_minutes = duration_in_minutes;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
