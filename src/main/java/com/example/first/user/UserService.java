package com.example.first.user;

import com.example.first.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseWrapper getUsers(){
        return new ResponseWrapper(userRepository.findAll(), 200, "All users fetched !");
    }

    public ResponseWrapper getUserById(long id){
        boolean exist = userRepository.existsById(id);

        if(exist){
            return new ResponseWrapper(userRepository.findById(id), 200, "User fetched successfully");
        }else{
            return new ResponseWrapper(null, 404, "User not Found !");
        }
    }

    public User addNewUser(User user){
        User emailOrUsernameExist = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());
        if(emailOrUsernameExist != null){
            return null;
        }

        User newUser = userRepository.save(user);
        return newUser;
    }

    public  ResponseWrapper updateUser(User user, long id){
        User prevUser = userRepository.findById(id).orElse(null);

        if(prevUser == null){
            return new ResponseWrapper(null, 400, "User not found !");
        }

        User emailOrUsernameExist = userRepository.findByNotIdAndEmailOrUsername(id, user.getEmail(), user.getUsername());
        if(emailOrUsernameExist != null){
            return new ResponseWrapper(null, 400, "Email/Username already exist !");
        }

        prevUser.setRole(user.getRole());
        prevUser.setEmail(user.getEmail());
        prevUser.setUsername(user.getUsername());
        prevUser.setPassword(user.getPassword());

        return new ResponseWrapper(prevUser, 200, "User updated successfully !");
    }
}
