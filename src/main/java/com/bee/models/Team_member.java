package com.bee.models;

import javax.persistence.*;

@Entity
@Table(name = "team_members")
public class Team_member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Boolean isTeamAdmin;
    private Boolean isEditor;
    @ManyToOne
    private User user;
    @ManyToOne
    private Team team;

    public Team_member() {
    }

    public Team_member(Boolean isTeamAdmin, Boolean isEditor, User user, Team team) {
        this.isTeamAdmin = isTeamAdmin;
        this.isEditor = isEditor;
        this.user = user;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public Boolean getTeamAdmin() {
        return isTeamAdmin;
    }

    public Boolean getEditor() {
        return isEditor;
    }

    public User getUser() {
        return user;
    }

    public Team getTeam() {
        return team;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeamAdmin(Boolean teamAdmin) {
        isTeamAdmin = teamAdmin;
    }

    public void setEditor(Boolean editor) {
        isEditor = editor;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Team_member{" +
                "id=" + id +
                ", isTeamAdmin=" + isTeamAdmin +
                ", isEditor=" + isEditor +
                ", user=" + user +
                ", team=" + team +
                '}';
    }



}
