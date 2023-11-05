package com.example.first.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NewUserReqDto {

    @NotEmpty(message = "Username is required !")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$", message = "username should be 8-30 char long, only include letter, number and _")
    private String username;

    @NotEmpty(message = "Password is required !")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @NotEmpty(message = "Email is required !")
    @Email(message = "Invalid email type !")
    private String email;

    @NotEmpty(message = "Role is required !")
    @Pattern(regexp = "^(ADMIN|USER)$", message = "Invalid user type !")
    private String role;

    public NewUserReqDto() {
    }

    public NewUserReqDto(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
