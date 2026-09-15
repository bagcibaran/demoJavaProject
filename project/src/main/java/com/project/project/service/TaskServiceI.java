package com.project.project.service;

import com.project.project.dto.TaskCreateRequest;
import com.project.project.dto.TaskResponse;
import com.project.project.dto.TaskUpdateRequest;

import java.util.List;

public interface TaskServiceI {

    List<TaskResponse> getAllTasks(String username);

    TaskResponse createTask(
            TaskCreateRequest request,
            String username
    );

    TaskResponse updateTask(
            Long id,
            TaskUpdateRequest request,
            String username
    );

    void deleteTask(Long id, String username);
}