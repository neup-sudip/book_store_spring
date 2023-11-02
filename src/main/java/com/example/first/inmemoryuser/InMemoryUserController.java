package com.example.first.inmemoryuser;

import com.example.first.utils.ResponseWrapper;
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
