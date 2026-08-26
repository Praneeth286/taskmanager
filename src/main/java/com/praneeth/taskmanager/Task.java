package com.praneeth.taskmanager;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private Boolean isDone;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Task() {}

    public Task(Long id, String title, Boolean isDone) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Boolean isDone() { return isDone; }
    public Category getCategory() { return category; }
    public User getOwner() { return owner; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDone(Boolean isDone) { this.isDone = isDone; }
    public void setCategory(Category category) { this.category = category; }
    public void setOwner(User owner) { this.owner = owner; }
}