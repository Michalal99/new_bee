package com.bee.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "team")
    private List<Team_member> team_member;

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


}