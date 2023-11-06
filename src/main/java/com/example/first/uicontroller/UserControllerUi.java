package com.example.first.uicontroller;

import com.example.first.user.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserControllerUi {

    private final UserService userService;

    @Autowired
    public UserControllerUi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUsers(Model model) {
        List<User> users =  userService.getUsers();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(user -> {
            User.ROLE role = User.ROLE.valueOf(user.getRole());
            UserResponseDto userResponseDto = new UserResponseDto(user.getUsername(), user.getEmail(), role);
            userResponseDtos.add(userResponseDto);
        });
        model.addAttribute("users", userResponseDtos);
        return "users";
    }

    @GetMapping("/edit/{username}")
    public String editUser(@PathVariable String username, Model model){
        User user = userService.getByUsername(username);
        if(user == null){
            return "redirect:/users";
        }else {
            model.addAttribute("user", user);
            return "adduser";
        }
    }

    @PostMapping("/edit/{username}")
    public String editUser(@Valid @ModelAttribute NewUserReqDto user, @PathVariable String username, Model model){

        User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());

        User exist = userService.getByUsername(username);
        if(exist == null){
            model.addAttribute("message", "User not found");
            return "edituser";
        }else{
            userService.updateUser(newUser, exist.getUserId());
            return "redirect:/users";
        }
    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model){
        System.out.println(username);
        User user = userService.getByUsername(username);
        if(user == null){
            return "redirect:/users";
        }else {
            model.addAttribute("user", user);
            return "user";
        }
    }

    @GetMapping("/register")
    public String addUser() {
        return "adduser";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute NewUserReqDto user){
        User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
        userService.addNewUser(newUser);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginDto userLoginDto, Model model){

        User user = userService.login(userLoginDto.getUsername(), userLoginDto.getPassword());

        if(user != null){
            return "redirect:/users";
        }else{
            model.addAttribute("message", "User not found");
            return "login";
        }
    }

}
