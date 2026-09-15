package com.project.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskCreateRequest {

    @NotBlank(message = "Title boş olamaz")
    @Size(
            min = 3,
            max = 100,
            message = "Title 3-100 karakter arasında olmalı"
    )
    private String title;

    public TaskCreateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TaskCreateRequest{title='" + title + "'}";
    }
}