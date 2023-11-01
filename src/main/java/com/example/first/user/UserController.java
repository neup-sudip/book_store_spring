package com.example.first.user;

import com.example.first.ResponseWrapper;
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
        User newUser = new User(userReqDto.getUsername(),userReqDto.getPassword(), userReqDto.getEmail(), userReqDto.getRole());

        User user =  userService.addNewUser(newUser);

        if(user == null){
            return new ResponseWrapper(null,406, "Email/Username already exist !" );
        }else {
            UserResponseDto resUser = new UserResponseDto(user.getUsername(), user.getEmail(), user.getRole());
            return new ResponseWrapper(resUser, 200, "User created successfully !");
        }
    }

    @GetMapping("/{id}")
    public ResponseWrapper getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseWrapper updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(user, id);
    }


}
