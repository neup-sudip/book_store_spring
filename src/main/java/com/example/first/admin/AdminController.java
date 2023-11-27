package com.example.first.admin;

import com.example.first.book.Book;
import com.example.first.book.BookService;
import com.example.first.authanduser.User;
import com.example.first.authanduser.UserResponseDto;
import com.example.first.authanduser.UserService;
import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return bookService.updateBook(newBook, id);
    }

    @GetMapping("/users")
    public ApiResponse getAllUsers() {
        return new ApiResponse(true, userService.getUsers(), "All users fetched", 200);
    }

    @GetMapping("/users/{id}")
    public ApiResponse getUserById(@PathVariable long id) {
        UserResponseDto user = userService.getUserById(id);

        if (user == null) {
            return new ApiResponse(false, null, "User not found !", 400);
        } else {

            return new ApiResponse(true, user, "User fetched successfully", 200);
        }
    }

    @PutMapping("/users/{id}")
    public ApiResponse updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(user, id);
    }
}
