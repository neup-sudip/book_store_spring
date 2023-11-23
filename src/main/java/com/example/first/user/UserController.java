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
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/register")
    public ApiResponse createUser(@Valid @RequestBody NewUserReqDto userReqDto) {

        User newUser = new User(userReqDto.getUsername(), userReqDto.getPassword(), userReqDto.getEmail(), userReqDto.getRole());

        User user = userService.addNewUser(newUser);
        User.ROLE role = User.ROLE.valueOf(user.getRole());

        UserResponseDto resUser = new UserResponseDto(user.getUsername(), user.getEmail(), role);
        return new ApiResponse(true, resUser, "User created successfully !",  200);
    }

    @PostMapping("/login")
    public ApiResponse loginUser(@Valid @RequestBody UserLoginDto user, HttpServletResponse response, HttpServletRequest request) {
        User returnUser = userService.login(user.getUsername(), user.getPassword());
        if (returnUser != null) {
            JwtConfig jwtConfig = new JwtConfig();
            returnUser.setPassword("");
            String token = jwtConfig.generateToken(returnUser);

            final Cookie cookie = new Cookie("auth", token);
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(100);
            cookie.setPath("/");
            response.addCookie(cookie);

            return new ApiResponse(true, token, "user success", 200);
        } else {
            return new ApiResponse(false, null, "user not found",  200);
        }
    }


}
