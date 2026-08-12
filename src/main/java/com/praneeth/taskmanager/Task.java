package com.praneeth.taskmanager;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message ="Title cannot be empty")
    private String title;
    private Boolean isDone;
    public Task(){} //empty constructor - spring needs to build objects from json
    public Task(Long id, String title, Boolean isDone) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
    }
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public Boolean isDone() {
        return isDone;
    }
    public void setId(Long id) {this. id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setDone(Boolean isDone) {this.isDone = isDone;}
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    public Category getCategory(){return category;}
    public void setCategory(Category category){this.category = category;}
}
//This is a test comment for Git practice