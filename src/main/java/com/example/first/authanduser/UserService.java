package com.example.first.authanduser;

import com.example.first.utils.ApiResponse;
import com.example.first.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll(Sort.by("userId"));
        List<UserResponseDto> resUsers = new ArrayList<>();
        for (User user : users) {
            resUsers.add(new UserResponseDto(user.getUsername(), user.getEmail(), User.ROLE.valueOf(user.getRole())));
        }
        return resUsers;
    }

    public UserResponseDto getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            User.ROLE role = User.ROLE.valueOf(user.getRole());
            return new UserResponseDto(user.getUsername(), user.getEmail(), role);
        } else {
            return null;
        }
    }

    public User getUserByIdNonDto(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserResponseDto addNewUser(User user) {
        User emailOrUsernameExist = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());

        if (emailOrUsernameExist != null) {
            throw new CustomException("UserName/Email is taken");
        }

        User newUser = userRepository.save(user);
        User.ROLE role = User.ROLE.valueOf(newUser.getRole());

        return new UserResponseDto(newUser.getUsername(), newUser.getEmail(), role);
    }

    public ApiResponse updateUser(User user, long id) {
        User prevUser = userRepository.findById(id).orElse(null);
        if (prevUser == null) {
            return new ApiResponse(false, null, "User not found !", 400);
        }

        User emailOrUsernameExist = userRepository.findByNotIdAndEmailOrUsername(id, user.getEmail(), user.getUsername());
        if (emailOrUsernameExist != null) {
            return new ApiResponse(false, null, "Email/Username already exist !", 400);
        }

        prevUser.setRole(user.getRole());
        prevUser.setEmail(user.getEmail());
        prevUser.setUsername(user.getUsername());
        prevUser.setPassword(user.getPassword());
        User newUser = userRepository.save(prevUser);

        User.ROLE role = User.ROLE.valueOf(newUser.getRole());
        UserResponseDto responseDto = new UserResponseDto(newUser.getUsername(), newUser.getEmail(), role);
        return new ApiResponse(true, responseDto, "User updated successfully !", 200);
    }

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
