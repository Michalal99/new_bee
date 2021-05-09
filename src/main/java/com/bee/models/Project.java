package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
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
    @JoinColumn(name="team_id", nullable = true)
    private final Team team = new Team();

    @NotBlank
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="project", orphanRemoval = true)
    private final Set<Meeting> meeting = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="project", orphanRemoval = true)
    private final Set<Brainstorm> brainstorm = new HashSet<>();

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team.setDescription(team.getDescription());
        this.team.setName(team.getName());
    }

    public Set<Meeting> getMeeting() {
        return meeting;
    }

    public void setMeeting(Set<Meeting> meeting) {
        this.meeting.retainAll(meeting);
        this.meeting.addAll(meeting);
    }

    public Set<Brainstorm> getBrainstorm() {
        return brainstorm;
    }

    public void setBrainstorm(Set<Brainstorm> brainstorm) {
        this.brainstorm.retainAll(brainstorm);
        this.brainstorm.addAll(brainstorm);
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public Kanban getKanban() {
        return kanban;
    }

    public void setKanban(Kanban kanban) {
        this.kanban = kanban;
    }
}
