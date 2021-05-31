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

    @Column(name="team_id", insertable = false, updatable = false)
    private Long team_id;
    @ManyToOne(optional = false)
    @JoinColumn(name="team_id", nullable = false)
    private Team team;

    @NotBlank
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="project", orphanRemoval = true)
    private Set<Meeting> meeting = new HashSet<>();

//    @OneToMany(cascade = CascadeType.ALL, mappedBy="project", orphanRemoval = true)
//    private final Set<Brainstorm> brainstorm = new HashSet<>();

    @OneToMany(mappedBy="project")
    private List<Brainstorm> brainstorm;

    @OneToMany(mappedBy="project")
    private List<Comment> comment;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy="project", orphanRemoval = true)
    private Kanban kanban;

    public Project() {

    }

    public Project(long l, String eee) {
        this.id = l;
        this.description = eee;
    }

    /*public Project(Team team) {
        this.team = team;
    }*/

    /*public Project(Long id, String description, Long team_id, Team team) {
        this.id = id;
        this.description = description;
        this.team = team;
        this.team_id = this.team.getId();
    }*/

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
        this.team = team;
        //this.team.setId(team.getId());
        //this.team.setDescription(team.getDescription());
        //this.team.setName(team.getName());
    }

    public Set<Meeting> getMeeting() {
        return meeting;
    }

    public void setMeeting(Set<Meeting> meeting) {
        this.meeting.retainAll(meeting);
        this.meeting.addAll(meeting);
    }

    public List<Brainstorm> getBrainstorm() {
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

    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
    }
}
