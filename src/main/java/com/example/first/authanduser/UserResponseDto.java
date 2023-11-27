package com.example.first.authanduser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserResponseDto {
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$", message = "username should be 8-30 char long, only include letter, number and _")
    private String username;

    @NotEmpty
    @Email(message = "Invalid email type !")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(ADMIN|USER)$", message = "Invalid usertype !")
    private User.ROLE role;

    public UserResponseDto() {
    }

    public UserResponseDto(String username, String email, User.ROLE role) {
        this.username = username;
        this.email = email;
        this.role = role;
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
