package com.project.project.service;

import com.project.project.dto.TaskUpdateRequest;
import com.project.project.dto.TaskCreateRequest;
import com.project.project.dto.TaskResponse;

import java.util.List;

public interface TaskServiceI {
    List<TaskResponse> getAllTasks();

    TaskResponse createTask(TaskCreateRequest request);

    TaskResponse updateTask(Long id, TaskUpdateRequest request);

    void deleteTask(Long id);
}
