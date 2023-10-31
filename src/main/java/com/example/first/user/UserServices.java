package com.example.first.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServices {

    public UserServices() {
    }

    static List<User> users = new ArrayList<User>();

    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public ResponseEntity<Object> getUser(String userName) {
        User user = findUser(userName);
        if (user == null) {
            String errorMessage = "User doesn't exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        } else {
            return ResponseEntity.ok(user);
        }

    }

    public ResponseEntity<Object> addUser(User newUser) {
        User user = findUser(newUser.getUsername());

        if (user == null) {
            users.add(newUser);
            return ResponseEntity.ok(newUser);
        } else {
            String errorMessage = "User with the same username already exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    public ResponseEntity<Object> updateUser(User newUser, String username) {
        User user = findUser(username);

        if (user == null) {
            String errorMessage = "User doesn't exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        } else {
            user.setUsername(newUser.getUsername());
            user.setPassword(newUser.getPassword());
            return ResponseEntity.ok(user);
        }
    }

    public ResponseEntity<Object> deleteUser(String username){
        User user = findUser(username);
        if (user == null) {
            String errorMessage = "User doesn't exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        } else {
            users.remove(user);
            String message = "User deleted successfully !";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
    }
}
