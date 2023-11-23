package com.example.first.user;

import com.example.first.utils.ApiResponse;
import com.example.first.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        System.out.println(user.getEmail() + user.getUsername());
        User emailOrUsernameExist = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());

        if (emailOrUsernameExist != null) {
            throw new CustomException("UserName/Email is taken");
        }else{
            System.out.println("Ok");
        }
        return userRepository.save(user);
    }

    public ApiResponse updateUser(User user, long id) {
        User prevUser = userRepository.findById(id).orElse(null);
        if (prevUser == null) {
            return new ApiResponse(false, null, "User not found !", 400);
        }
        User emailOrUsernameExist = userRepository.findByNotIdAndEmailOrUsername(id, user.getEmail(), user.getUsername());
        if (emailOrUsernameExist != null) {
            return new ApiResponse(false,null, "Email/Username already exist !", 400);
        }
        prevUser.setRole(user.getRole());
        prevUser.setEmail(user.getEmail());
        prevUser.setUsername(user.getUsername());
        prevUser.setPassword(user.getPassword());
        userRepository.save(prevUser);
        return new ApiResponse(true,prevUser, "User updated successfully !", 200);
    }

    public User login(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
