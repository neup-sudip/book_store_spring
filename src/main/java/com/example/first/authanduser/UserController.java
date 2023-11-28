package com.example.first.authanduser;

import com.example.first.utils.JwtConfig;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ApiResponse createUser(@Valid @RequestBody NewUserDto newUserDto) {
        User newUser = new User(newUserDto.getUsername(), newUserDto.getPassword(), newUserDto.getEmail(), "USER");

        UserResponseDto user = userService.addNewUser(newUser);

        return new ApiResponse(true, user, "User created successfully !", 200);
    }

    @PostMapping("/auth/login")
    public ApiResponse loginUser(@Valid @RequestBody UserLoginDto user, HttpServletResponse response) {
        User returnUser = userService.login(user.getUsername(), user.getPassword());
        if (returnUser != null) {
            JwtConfig jwtConfig = new JwtConfig();
            returnUser.setPassword("");
            String token = jwtConfig.generateToken(returnUser);

            final Cookie cookie = new Cookie("auth", token);
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(50400);
            cookie.setPath("/api");
            response.addCookie(cookie);

            return new ApiResponse(true, token, "user success", 200);
        } else {
            return new ApiResponse(false, null, "user not found", 400);
        }
    }

    @GetMapping("/users/profile")
    public ApiResponse getProfile(HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");
        UserResponseDto user = userService.getUserById(decodedUser.getUserId());
        if (user == null) {
            return new ApiResponse(true, null, "Error fetching user", 400);
        } else {
            return new ApiResponse(true, user, "User fetched successfully", 200);
        }
    }

    @GetMapping("/users/edit")
    public ApiResponse getEditUser(HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");
        User user = userService.getUserByIdNonDto(decodedUser.getUserId());
        if (user == null) {
            return new ApiResponse(true, null, "Error fetching user", 400);
        } else {
            return new ApiResponse(true, user, "User fetched successfully", 200);
        }
    }

    @PutMapping("/users/edit/{id}")
    public ApiResponse editUser(@RequestBody NewUserDto newUserDto, @PathVariable long id, HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");

        if (decodedUser.getUserId() != id) {
            return new ApiResponse(false, null, "Error updating user", 400);
        } else {
            User user = new User(newUserDto.getUsername(), newUserDto.getPassword(), newUserDto.getEmail(), "USER");
            return userService.updateUser(user, id);
        }
    }


}
