package com.example.first.user;

import com.example.first.utils.JwtConfig;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/profile")
    public ApiResponse getProfile(HttpServletRequest request){
        User decodedUser = (User) request.getAttribute("user");
        UserResponseDto user = userService.getUserById(decodedUser.getUserId());

        return new ApiResponse(true, user, "User fetched successfully", 200);
    }

    @PostMapping("/auth/register")
    public ApiResponse createUser(@Valid @RequestBody NewUserReqDto userReqDto) {
        User newUser = new User(userReqDto.getUsername(), userReqDto.getPassword(), userReqDto.getEmail(), userReqDto.getRole());

        UserResponseDto user = userService.addNewUser(newUser);

        return new ApiResponse(true, user, "User created successfully !",  200);
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
            cookie.setMaxAge(50400);
            cookie.setPath("/api");
            response.addCookie(cookie);

            return new ApiResponse(true, token, "user success", 200);
        } else {
            return new ApiResponse(false, null, "user not found",  400);
        }
    }


}
