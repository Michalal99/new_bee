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

    @NotBlank
    private Long kanbans_id;

    @NotBlank
    @Size(max=50)
    private String users_id;

    public Kanban_inprogress(Long id, String title, String description, Long kanbans_id, String users_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.kanbans_id = kanbans_id;
        this.users_id = users_id;
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

    public Long getKanbans_id() {
        return kanbans_id;
    }

    public void setKanbans_id(Long kanbans_id) {
        this.kanbans_id = kanbans_id;
    }

    public String getUsers_id() {
        return users_id;
    }

    public void setUsers_id(String users_id) {
        this.users_id = users_id;
    }
}
