package com.example.first.authanduser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserResponseDto {
    private  long userId;

    private String username;

    private String email;

    private User.ROLE role;

    public UserResponseDto() {
    }

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = User.ROLE.valueOf(user.getRole());
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

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

    public User.ROLE getRole() {
        return role;
    }

    public void setRole(User.ROLE role) {
        this.role = role;
    }
}
