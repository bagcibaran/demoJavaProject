package com.project.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.project.entity.TaskEntity;

public class TaskResponse {

    private Long id;
    private String title;
    @JsonProperty("isCompleted")
    private boolean isCompleted;
    private String username;

    public TaskResponse(TaskEntity taskEntity){
        this.id = taskEntity.getId();
        this.title = taskEntity.getTitle();
        this.isCompleted = taskEntity.isCompleted();
        if (taskEntity.getUser() != null) {
            this.username = taskEntity.getUser().getUsername();
        }
    }

    @JsonProperty("isCompleted")
    public boolean isCompleted() {
        return isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "TaskResponse{id=" + id + ", title='" + title + "', isCompleted=" + isCompleted + ", username='" + username + "'}";
    }
}