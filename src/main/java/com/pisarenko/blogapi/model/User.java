package com.pisarenko.blogapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "author")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "user_posts")
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> userPosts;


    public User() {
    }

    public User(long id, String username, String password, UserRole role, List<Post> userPosts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userPosts = userPosts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<Post> userPosts) {
        this.userPosts = userPosts;
    }
}
