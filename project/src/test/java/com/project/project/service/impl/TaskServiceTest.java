package com.project.project.service.impl;

import com.project.project.dto.TaskCreateRequest;
import com.project.project.dto.TaskResponse;
import com.project.project.dto.TaskUpdateRequest;
import com.project.project.entity.TaskEntity;
import com.project.project.entity.User;
import com.project.project.repository.TaskRepository;
import com.project.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository, userRepository);
    }

    @Test
    void getAllTasks() {
        User user = new User(
                "testuser",
                "test@test.com",
                "hashedpassword"
        );

        TaskEntity task1 = new TaskEntity("Test task 1");
        TaskEntity task2 = new TaskEntity("Test task 2");

        List<TaskEntity> fakeTasks = List.of(task1, task2);

        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));

        when(taskRepository.findAllByUser(user))
                .thenReturn(fakeTasks);

        List<TaskResponse> result =
                taskService.getAllTasks("testuser");

        assertEquals(2, result.size());
        assertEquals("Test task 1", result.get(0).getTitle());
    }

    @Test
    void createTask() {
        User user = new User(
                "testuser",
                "test@test.com",
                "hashedpassword"
        );

        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Yeni görev");

        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));

        when(taskRepository.save(any(TaskEntity.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        TaskResponse result =
                taskService.createTask(request, "testuser");

        assertEquals("Yeni görev", result.getTitle());
        verify(taskRepository).save(any(TaskEntity.class));
    }

    @Test
    void updateTask() {
        User user = new User(
                "testuser",
                "test@test.com",
                "hashedpassword"
        );

        TaskEntity existingTask = new TaskEntity("Eski görev");
        existingTask.setId(10L);
        existingTask.setUser(user);

        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Güncellenmiş görev");
        request.setCompleted(true);

        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));

        when(taskRepository.findByIdAndUser(10L, user))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(any(TaskEntity.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        TaskResponse result =
                taskService.updateTask(
                        10L,
                        request,
                        "testuser"
                );

        assertEquals("Güncellenmiş görev", result.getTitle());
        assertTrue(result.isCompleted());
        verify(taskRepository).save(existingTask);
    }

    @Test
    void deleteTask() {
        User user = new User(
                "testuser",
                "test@test.com",
                "hashedpassword"
        );

        TaskEntity existingTask = new TaskEntity("Silinecek görev");
        existingTask.setId(5L);
        existingTask.setUser(user);

        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));

        when(taskRepository.findByIdAndUser(5L, user))
                .thenReturn(Optional.of(existingTask));

        taskService.deleteTask(5L, "testuser");

        verify(taskRepository).delete(existingTask);
    }
}