package com.example.first.admin;

import com.example.first.authanduser.User;
import com.example.first.authanduser.UserResponseDto;
import com.example.first.authanduser.UserService;
import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private  final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ApiResponse getAllUsers() {
        return new ApiResponse(true, userService.getUsers(), "All users fetched", 200);
    }

    @GetMapping("/{id}")
    public ApiResponse getUserById(@PathVariable long id) {
        UserResponseDto user = userService.getUserById(id);

        if (user == null) {
            return new ApiResponse(false, null, "User not found !", 400);
        } else {

            return new ApiResponse(true, user, "User fetched successfully", 200);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(user, id);
    }
}
