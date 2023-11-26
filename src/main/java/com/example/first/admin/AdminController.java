package com.example.first.admin;

import com.example.first.book.Book;
import com.example.first.book.BookService;
import com.example.first.user.User;
import com.example.first.user.UserResponseDto;
import com.example.first.user.UserService;
import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final BookService bookService;
    private  final UserService userService;

    @Autowired
    public AdminController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping("/books")
    public ApiResponse addBook(@RequestBody Book book) {
        return new ApiResponse(true, bookService.addNewBook(book), "Book added successfully", 200);
    }

    @PutMapping("/books/{id}")
    public ApiResponse editBook(@PathVariable long id, @RequestBody Book newBook) {
        return new ApiResponse(true, bookService.updateBook(newBook, id), "Book edited successfully", 200);
    }

    @GetMapping("/users")
    public ApiResponse getAllUsers() {
        List<User> users = userService.getUsers();
        List<UserResponseDto> resUsers = new ArrayList<>();
        for (User user : users) {
            resUsers.add(new UserResponseDto(user.getUsername(), user.getEmail(), User.ROLE.valueOf(user.getRole())));
        }
        return new ApiResponse(true, resUsers, "All users fetched", 200);
    }

    @GetMapping("/users/{id}")
    public ApiResponse getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return new ApiResponse(false, null, "User not found !", 400);
        } else {
            User.ROLE role = User.ROLE.valueOf(user.getRole());
            UserResponseDto resUser = new UserResponseDto(user.getUsername(), user.getEmail(), role);
            return new ApiResponse(true, resUser, "User fetched successfully", 200);
        }
    }

    @PutMapping("/users/{id}")
    public ApiResponse updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(user, id);
    }
}
