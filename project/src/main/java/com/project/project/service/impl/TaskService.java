package com.project.project.service.impl;

import com.project.project.dto.TaskCreateRequest;
import com.project.project.dto.TaskResponse;
import com.project.project.dto.TaskUpdateRequest;
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

@Service("taskService")
public class TaskService implements TaskServiceI {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskResponse> getAllTasks(String username) {
        User user = findUserByUsername(username);

        List<TaskEntity> taskEntities =
                taskRepository.findAllByUser(user);

        List<TaskResponse> response = new ArrayList<>();

        for (TaskEntity taskEntity : taskEntities) {
            response.add(new TaskResponse(taskEntity));
        }

        return response;
    }

    @Override
    public TaskResponse createTask(
            TaskCreateRequest request,
            String username
    ) {
        User user = findUserByUsername(username);

        TaskEntity taskEntity = new TaskEntity(request.getTitle());
        taskEntity.setUser(user);

        TaskEntity savedTaskEntity =
                taskRepository.save(taskEntity);

        return new TaskResponse(savedTaskEntity);
    }

    @Override
    public TaskResponse updateTask(
            Long id,
            TaskUpdateRequest request,
            String username
    ) {
        User user = findUserByUsername(username);

        TaskEntity taskEntity = taskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task bulunamadı, id: " + id
                        )
                );

        taskEntity.setTitle(request.getTitle());
        taskEntity.setIsCompleted(request.isCompleted());

        TaskEntity savedTaskEntity =
                taskRepository.save(taskEntity);

        return new TaskResponse(savedTaskEntity);
    }

    @Override
    public void deleteTask(Long id, String username) {
        User user = findUserByUsername(username);

        TaskEntity taskEntity = taskRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task bulunamadı, id: " + id
                        )
                );

        taskRepository.delete(taskEntity);
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Kullanıcı bulunamadı: " + username
                        )
                );
    }
}