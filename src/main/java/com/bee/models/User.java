package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private Set<Kanban_done> kanban_done;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private Set<Kanban_inprogress> kanban_inprogress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private Set<Kanban_todo> kanban_todo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private Set<Grade> grade;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private Set<Comment> comment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private List<Team_member> team_member;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}