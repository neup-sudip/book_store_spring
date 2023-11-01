package com.example.first.inmemoryuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InMemoryUserServices {

    public InMemoryUserServices() {
    }

    static List<InMemoryUser> inMemoryUsers = new ArrayList<InMemoryUser>();

    private InMemoryUser findUser(String username) {
        for (InMemoryUser inMemoryUser : inMemoryUsers) {
            if (inMemoryUser.getUsername().equals(username)) {
                return inMemoryUser;
            }
        }
        return null;
    }

    public List<InMemoryUser> getAllUsers() {
        return inMemoryUsers;
    }

    public ResponseEntity<Object> getUser(String userName) {
        InMemoryUser inMemoryUser = findUser(userName);
        if (inMemoryUser == null) {
            String errorMessage = "User doesn't exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        } else {
            return ResponseEntity.ok(inMemoryUser);
        }

    }

    public ResponseEntity<Object> addUser(InMemoryUser newInMemoryUser) {
        InMemoryUser inMemoryUser = findUser(newInMemoryUser.getUsername());

        if (inMemoryUser == null) {
            inMemoryUsers.add(newInMemoryUser);
            return ResponseEntity.ok(newInMemoryUser);
        } else {
            String errorMessage = "User with the same username already exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    public ResponseEntity<Object> updateUser(InMemoryUser newInMemoryUser, String username) {
        InMemoryUser inMemoryUser = findUser(username);

        if (inMemoryUser == null) {
            String errorMessage = "User doesn't exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        } else {
            inMemoryUser.setUsername(newInMemoryUser.getUsername());
            inMemoryUser.setPassword(newInMemoryUser.getPassword());
            return ResponseEntity.ok(inMemoryUser);
        }
    }

    public ResponseEntity<Object> deleteUser(String username){
        InMemoryUser inMemoryUser = findUser(username);
        if (inMemoryUser == null) {
            String errorMessage = "User doesn't exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        } else {
            inMemoryUsers.remove(inMemoryUser);
            String message = "User deleted successfully !";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
    }
}
