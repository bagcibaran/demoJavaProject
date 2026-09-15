package com.project.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskUpdateRequest {

    private String title;

    @JsonProperty("isCompleted")
    private boolean isCompleted;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("isCompleted")
    public boolean isCompleted() {
        return isCompleted;
    }

    @JsonProperty("isCompleted")
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}