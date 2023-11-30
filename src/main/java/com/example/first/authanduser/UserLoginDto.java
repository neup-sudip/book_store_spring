package com.example.first.authanduser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserLoginDto {

    @NotEmpty(message = "Username is required !")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$", message = "username should be 8-30 char long, only include letter, number and _")
    private String username;

    @NotEmpty(message = "Password is required !")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;


//    public UserLoginDto() {
//    }

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
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

}
