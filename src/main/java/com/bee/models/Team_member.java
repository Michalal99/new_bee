package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="team_members")
public class Team_member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    private Boolean isTeamAdmin;

    @NotBlank
    private Boolean isEditor;

    @Column(name="user_id", insertable = false, updatable = false)
    private Long user_id;
    @ManyToOne(optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name="team_id", insertable = false, updatable = false)
    private Long team_id;
    @ManyToOne(optional = false)
    @JoinColumn(name="team_id", nullable = false)
    private Team team;

    public Team_member() {
    }

    //    public Team_member(Boolean isTeamAdmin, Boolean isEditor, User user, Team team) {
//        this.isTeamAdmin = isTeamAdmin;
//        this.isEditor = isEditor;
//        this.user = user;
//        this.team = team;
//    }

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

    public Long getUser_id() {
        return user_id;
    }

    public Long getTeam_id() {
        return team_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
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
