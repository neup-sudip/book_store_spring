package com.example.first.admin;

import com.example.first.authanduser.User;
import com.example.first.authanduser.UserResponseDto;
import com.example.first.authanduser.UserService;
import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> getAllUsers() {
        ApiResponse apiResponse = new ApiResponse(true, userService.getUsers(), "All users fetched");
        return ResponseEntity.status(200).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable long id) {
        UserResponseDto user = userService.getUserById(id);

        if (user == null) {
            ApiResponse apiResponse = new ApiResponse(false, null, "User not found !");
            return ResponseEntity.status(400).body(apiResponse);
        } else {
            ApiResponse apiResponse = new ApiResponse(true, user, "User fetched successfully");
            return ResponseEntity.status(200).body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(user, id);
    }
}
