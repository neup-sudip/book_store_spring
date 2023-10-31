package com.example.first.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping()
    public List<User> getUsers(){
        return userServices.getAllUsers();
    }

    @GetMapping("/test")
    public ResponseWrapper test(){
        return new ResponseWrapper(null, 200, "Success");
    }

    @GetMapping("/test1")
    public ResponseWrapper test1(){
        return new ResponseWrapper(new User("user1", "user1"), 200, "Success");
    }

    @GetMapping("/test2")
    public ResponseWrapper test2(){
        return new ResponseWrapper(List.of(new User("user1", "user1"), new User("user2", "user2")), 200, "Success");
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username){
        return userServices.getUser(username);
    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@RequestBody User user){
        return userServices.addUser(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> editUser(@PathVariable String username, @RequestBody User user){
        return userServices.updateUser(user, username);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username){
        return userServices.deleteUser(username);
    }
}
