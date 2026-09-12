package com.project.project.service.impl;

import com.project.project.dto.*;
import com.project.project.entity.User;
import com.project.project.exception.UserNotFoundException;
import com.project.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getAllUsers() {
        List<User> userEntities = userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();
        for (User user : userEntities) {
            response.add(new UserResponse(user));
        }
        return response;
    }

    public UserResponse createUser(UserCreateRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User(request.getUsername(), request.getEmail(), hashedPassword);
        User savedUserEntity = userRepository.save(newUser);
        return new UserResponse(savedUserEntity);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        Optional<User> existingTask = userRepository.findById(id);
        if (existingTask.isPresent()) {
            User newUser = existingTask.get();
            newUser.setEmail(request.getEmail());
            newUser.setUsername(request.getUsername());
            User savedUser = userRepository.save(newUser);
            return new UserResponse(savedUser);
        }
        throw new UserNotFoundException("User bulunamadı, id: " + id);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User bulunamadı, id: " + id);
        }
        userRepository.deleteById(id);
    }
}
