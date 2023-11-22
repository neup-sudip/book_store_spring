package com.example.first.admin;

import com.example.first.book.Book;
import com.example.first.book.BookService;
import com.example.first.user.User;
import com.example.first.user.UserResponseDto;
import com.example.first.user.UserService;
import com.example.first.utils.ResponseData;
import com.example.first.utils.ResponseWrapper;
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
    public ResponseWrapper addBook(@RequestBody Book book) {
        ResponseData res = new ResponseData(bookService.addNewBook(book), "Book added", true);
        return new ResponseWrapper(res, 200);
    }

    @PutMapping("/books/{id}")
    public ResponseWrapper editBook(@PathVariable long id, @RequestBody Book newBook) {
        ResponseData res = new ResponseData(bookService.updateBook(newBook, id), "Book added", true);
        return new ResponseWrapper(res, 200);
    }

    @GetMapping("/users")
    public ResponseWrapper getAllUsers() {
        List<User> users = userService.getUsers();
        List<UserResponseDto> resUser = new ArrayList<>();
        for (User user : users) {
            resUser.add(new UserResponseDto(user.getUsername(), user.getEmail(), User.ROLE.valueOf(user.getRole())));
        }
        ResponseData res = new ResponseData(resUser, "All users fetched", true);
        return new ResponseWrapper(res, 200);
    }

    @GetMapping("/users/{id}")
    public ResponseWrapper getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return new ResponseWrapper(new ResponseData(null, "User not found !", false), 404);
        } else {
            User.ROLE role = User.ROLE.valueOf(user.getRole());
            UserResponseDto resUser = new UserResponseDto(user.getUsername(), user.getEmail(), role);
            return new ResponseWrapper(new ResponseData(resUser, "User fetched successfully", true), 200);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseWrapper updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(user, id);
    }
}
