package com.praneeth.taskmanager;
import jakarta.persistence.*;
@Entity
@Table(name="app_user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role = "USER";
    public User(){}
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.role = "USER";

    }
    public Long getId(){return id;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getRole(){return role;}
    public void setPassword(String password){this.password = password;}
    public void setRole(String role){this.role = role;}
}
