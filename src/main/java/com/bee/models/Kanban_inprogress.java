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

    //@NotBlank
    //private Long kanbans_id;
    @ManyToOne
    @JoinColumn(name="kanban_id")
    private Kanban kanban;

    //@NotBlank
    //@Size(max=50)
    //private String users_id;
    @ManyToOne
    @JoinColumn(name="user_id")
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
}
