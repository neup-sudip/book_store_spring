package com.example.first.user;

import com.example.first.utils.CustomException;
import com.example.first.utils.ResponseData;
import com.example.first.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseWrapper getUsers() {
        ResponseData res = new ResponseData(userRepository.findAll(),"All users fetched");
        return new ResponseWrapper(res, 200);
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addNewUser(User user) {

        User emailOrUsernameExist = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());

        if (emailOrUsernameExist != null) {
            Map<String, String> errors = new HashMap<>();
            errors.put("username", "User Name is taken");
            throw new CustomException(errors);
        }

        User newUser = userRepository.save(user);
        return newUser;
    }

    public ResponseWrapper updateUser(User user, long id) {
        User prevUser = userRepository.findById(id).orElse(null);

        if (prevUser == null) {
            return new ResponseWrapper(new ResponseData(null, "User not found !"), 400);
        }

        User emailOrUsernameExist = userRepository.findByNotIdAndEmailOrUsername(id, user.getEmail(), user.getUsername());
        if (emailOrUsernameExist != null) {
            return new ResponseWrapper(new ResponseData(null, "Email/Username already exist !"), 400);
        }

        prevUser.setRole(user.getRole());
        prevUser.setEmail(user.getEmail());
        prevUser.setUsername(user.getUsername());
        prevUser.setPassword(user.getPassword());

        return new ResponseWrapper(new ResponseData(prevUser, "User updated successfully !"), 200);
    }
}