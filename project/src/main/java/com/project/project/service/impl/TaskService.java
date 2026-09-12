package com.project.project.service.impl;

import com.project.project.dto.TaskUpdateRequest;
import com.project.project.dto.TaskCreateRequest;
import com.project.project.dto.TaskResponse;
import com.project.project.entity.TaskEntity;
import com.project.project.entity.User;
import com.project.project.exception.TaskNotFoundException;
import com.project.project.exception.UserNotFoundException;
import com.project.project.repository.TaskRepository;
import com.project.project.repository.UserRepository;
import com.project.project.service.TaskServiceI;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@ConditionalOnProperty(value = "task_serviceTwo_enable", havingValue = "true", matchIfMissing = false)
@Service("taskService")
public class TaskService implements TaskServiceI {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        List<TaskResponse> response = new ArrayList<>();
        for (TaskEntity taskEntity : taskEntities) {
            response.add(new TaskResponse(taskEntity));
        }
        return response;
    }

    @Override
    public TaskResponse createTask(TaskCreateRequest request) {
        Optional<User> existingUser = userRepository.findById(request.getUserId());
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("user bulunamadı id: " + request.getUserId());
        }
        User user = existingUser.get();
        TaskEntity taskEntity = new TaskEntity(request.getTitle());
        taskEntity.setUser(user);
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        return new TaskResponse(savedTaskEntity);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        Optional<TaskEntity> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            TaskEntity newTaskEntity = existingTask.get();
            newTaskEntity.setTitle(request.getTitle());
            newTaskEntity.setIsCompleted(request.isCompleted());
            TaskEntity savedTaskEntity = taskRepository.save(newTaskEntity);
            return new TaskResponse(savedTaskEntity);
        }
        throw new TaskNotFoundException("Task bulunamadı, id: " + id);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task bulunamadı, id: " + id);
        }
        taskRepository.deleteById(id);
    }
}