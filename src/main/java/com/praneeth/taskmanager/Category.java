package com.praneeth.taskmanager;
import jakarta.persistence.*;
import java.util.List;
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy="category")
    private List<Task> tasks;
    public Category(){}
    public Category(String name){
        this.name = name;
    }
    public  Long getId(){return id;}
    public String getName(){return name;}
public List<Task> getTasks(){return tasks;}
}
