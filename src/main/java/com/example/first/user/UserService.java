package com.example.first.user;

import com.example.first.utils.CustomException;
import com.example.first.utils.ResponseData;
import com.example.first.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll(Sort.by("userId"));
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addNewUser(User user) {

        User emailOrUsernameExist = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());

        if (emailOrUsernameExist != null) {
            throw new CustomException("User Name is taken");
        }

        return userRepository.save(user);
    }

    public ResponseWrapper updateUser(User user, long id) {
        User prevUser = userRepository.findById(id).orElse(null);

        if (prevUser == null) {
            return new ResponseWrapper(new ResponseData(null, "User not found !", false), 400);
        }

        User emailOrUsernameExist = userRepository.findByNotIdAndEmailOrUsername(id, user.getEmail(), user.getUsername());
        if (emailOrUsernameExist != null) {
            return new ResponseWrapper(new ResponseData(null, "Email/Username already exist !", false), 400);
        }

        prevUser.setRole(user.getRole());
        prevUser.setEmail(user.getEmail());
        prevUser.setUsername(user.getUsername());
        prevUser.setPassword(user.getPassword());
        userRepository.save(prevUser);

        return new ResponseWrapper(new ResponseData(prevUser, "User updated successfully !", true), 200);
    }

    public User login(String username, String password){

        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
