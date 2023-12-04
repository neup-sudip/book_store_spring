package com.example.first.admin;

import com.example.first.book.Book;
import com.example.first.book.BookService;
import com.example.first.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final BookService bookService;

    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addBook(@RequestBody Book book) {
        ApiResponse apiResponse = new ApiResponse(true, bookService.addNewBook(book), "Book added successfully", 200);
        return ResponseEntity.status(200).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editBook(@PathVariable long id, @RequestBody Book newBook) {
        return bookService.updateBook(newBook, id);
    }
}
