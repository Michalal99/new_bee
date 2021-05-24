package com.bee.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="team", orphanRemoval = true)
    private List<Team_member> team_member;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="team", orphanRemoval = true)
    private List<Project> project;

    public Team() {
    }

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public List<Team_member> getTeam_member() {
        return team_member;
    }

    public void setTeam_member(List<Team_member> team_member) {
        this.team_member = team_member;
    }

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }

}
