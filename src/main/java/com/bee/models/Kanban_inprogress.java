package com.bee.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="kanban_inprogresses")
public class Kanban_inprogress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    @Size(max=255)
    private String title;

    @NotBlank
    private String description;

    @Column(name="kanban_id", insertable = false, updatable = false)
    private Long kanban_id;
    @ManyToOne
    @JoinColumn(name="kanban_id", nullable = false)
    private Kanban kanban;

    @Column(name="user_id", insertable = false, updatable = false)
    private Long user_id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Kanban_inprogress() {
    }

    public Kanban_inprogress(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Kanban getKanban() {
        return kanban;
    }

    public void setKanban(Kanban kanban) {
        this.kanban = kanban;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getKanban_id() {
        return kanban_id;
    }

    public void setKanban_id(Long kanban_id) {
        this.kanban_id = kanban_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
