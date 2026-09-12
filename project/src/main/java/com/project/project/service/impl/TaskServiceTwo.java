package com.project.project.service.impl;

import com.project.project.dto.TaskUpdateRequest;
import com.project.project.dto.TaskCreateRequest;
import com.project.project.dto.TaskResponse;
import com.project.project.entity.TaskEntity;
import com.project.project.repository.TaskRepository;
import com.project.project.service.TaskServiceI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@ConditionalOnProperty(value = "task_serviceTwo_enable", havingValue = "false", matchIfMissing = false)
@Service("taskServiceTwo")
public class TaskServiceTwo implements TaskServiceI {

    private final TaskRepository taskRepository;

    public TaskServiceTwo(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        System.out.println("basıldı");
        return  null;
    }

    @Override
    public TaskResponse createTask(TaskCreateRequest request) {
        TaskEntity taskEntity = new TaskEntity(request.getTitle());
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        return new TaskResponse(savedTaskEntity);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        Optional<TaskEntity> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            TaskEntity taskEntity = existingTask.get();
            taskEntity.setTitle(request.getTitle());
            taskEntity.setIsCompleted(request.isCompleted());
            TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
            return new TaskResponse(savedTaskEntity);
        }
        return null;
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}