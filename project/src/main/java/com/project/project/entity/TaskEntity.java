package com.project.project.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TaskEntity() {
    }

    public TaskEntity(String title) {
        this.title = title;
        this.isCompleted = false;
        this.createdAt = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "TaskEntity{id=" + id + ", title='" + title + "', isCompleted=" + isCompleted + "}";
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
