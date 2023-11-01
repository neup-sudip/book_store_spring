package com.example.first.inmemoryuser;

import com.example.first.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/users")
public class InMemoryUserController {

    @Autowired
    private InMemoryUserServices inMemoryUserServices;

    @GetMapping()
    public List<InMemoryUser> getUsers(){
        return inMemoryUserServices.getAllUsers();
    }

    @GetMapping("/test")
    public ResponseWrapper test(){
        return new ResponseWrapper(null, 200, "Success");
    }

    @GetMapping("/test1")
    public ResponseWrapper test1(){
        return new ResponseWrapper(new InMemoryUser("user1", "user1"), 200, "Success");
    }

    @GetMapping("/test2")
    public ResponseWrapper test2(){
        return new ResponseWrapper(List.of(new InMemoryUser("user1", "user1"), new InMemoryUser("user2", "user2")), 200, "Success");
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username){
        return inMemoryUserServices.getUser(username);
    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@RequestBody InMemoryUser inMemoryUser){
        return inMemoryUserServices.addUser(inMemoryUser);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> editUser(@PathVariable String username, @RequestBody InMemoryUser inMemoryUser){
        return inMemoryUserServices.updateUser(inMemoryUser, username);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username){
        return inMemoryUserServices.deleteUser(username);
    }
}
