package com.example.first.admin;

import com.example.first.book.Book;
import com.example.first.book.BookService;
import com.example.first.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final BookService bookService;

    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ApiResponse addBook(@RequestBody Book book) {
        return new ApiResponse(true, bookService.addNewBook(book), "Book added successfully", 200);
    }

    @PutMapping("/{id}")
    public ApiResponse editBook(@PathVariable long id, @RequestBody Book newBook) {
        return bookService.updateBook(newBook, id);
    }
}
