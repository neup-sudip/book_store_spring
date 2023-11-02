package com.example.first.user;

import com.example.first.utils.ResponseData;
import com.example.first.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public ResponseWrapper getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public ResponseWrapper createUser(@Valid @RequestBody UserRequestDto userReqDto) {
        User.ROLE role = User.ROLE.valueOf(userReqDto.getRole());

        User newUser = new User(userReqDto.getUsername(), userReqDto.getPassword(), userReqDto.getEmail(), role);

        User user = userService.addNewUser(newUser);

        UserResponseDto resUser = new UserResponseDto(user.getUsername(), user.getEmail(), user.getRole());
        return new ResponseWrapper(new ResponseData(resUser, "User created successfully !"), 200);
    }

    @GetMapping("/{id}")
    public ResponseWrapper getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return new ResponseWrapper(new ResponseData(null, "User not found !"), 404);
        } else {
            UserResponseDto resUser = new UserResponseDto(user.getUsername(), user.getEmail(), user.getRole());
            return new ResponseWrapper(new ResponseData(resUser, "User fetched successfully"), 200);
        }
    }

    @PutMapping("/{id}")
    public ResponseWrapper updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(user, id);
    }


}
