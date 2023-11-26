package com.example.first.book;

import com.example.first.user.User;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ApiResponse getAllBooks(HttpServletRequest request) {
        return new ApiResponse(true, bookService.getBooks(), "All books fetched", 200);
    }

    @GetMapping("/{slug}")
    public ApiResponse getBookBySlug(@PathVariable String slug) {
        Book book = bookService.getBookBySlug(slug);
        if (book == null) {
            return new ApiResponse(false, null, "Book not found", 200);
        } else {
            return new ApiResponse(true, book, "Book fetched", 200);
        }
    }
}
