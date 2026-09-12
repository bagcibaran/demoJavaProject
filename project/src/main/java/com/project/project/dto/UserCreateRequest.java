package com.project.project.dto;

import jakarta.validation.constraints.*;

public class UserCreateRequest {

    @NotBlank(message = "Username alanı boş olamaz!!!")
    private String username;
    @NotBlank(message = "Email alanı boş olamaz!!!")
    @Email(message = "Email alanı formata uygun olmalı!!")
    private String email;
    @NotBlank(message = "Password alanı boş olamaz!!!")
    private String password;


    public UserCreateRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
