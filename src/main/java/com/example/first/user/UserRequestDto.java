package com.example.first.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequestDto {
    private long user_id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$", message = "username should be 8-30 char long, only include letter, number and _")
    private String username;

    @NotEmpty
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @NotEmpty
    @Email(message = "Invalid email type !")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(ADMIN|USER)$", message = "Invalid usertype !")
    private User.ROLE role;

    public UserRequestDto() {
    }

    public UserRequestDto(String username, String password, String email, User.ROLE role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
