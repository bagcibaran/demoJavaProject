package com.project.project.dto;

import jakarta.validation.constraints.*;

public class TaskCreateRequest {

    @NotBlank(message = "Title boş olamaz")
    @Size(min = 3, max = 100, message = "Title 3-100 karakter arasında olmalı")
    private String title;
    private Long userId;

    public TaskCreateRequest() {}

    public String getTitle() {
        return title ;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "TaskCreateRequest{title='" + title + "'}";
    }
}
