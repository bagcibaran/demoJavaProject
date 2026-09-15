package com.project.project.controller;

import com.project.project.dto.TaskCreateRequest;
import com.project.project.dto.TaskResponse;
import com.project.project.dto.TaskUpdateRequest;
import com.project.project.service.TaskServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceI taskService;

    public TaskController(
            @Qualifier("taskService") TaskServiceI taskService
    ) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            Authentication authentication
    ) {
        String username = authentication.getName();

        List<TaskResponse> tasks =
                taskService.getAllTasks(username);

        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();

        TaskResponse created =
                taskService.createTask(request, username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();

        TaskResponse updated =
                taskService.updateTask(id, request, username);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String username = authentication.getName();

        taskService.deleteTask(id, username);

        return ResponseEntity.noContent().build();
    }
}