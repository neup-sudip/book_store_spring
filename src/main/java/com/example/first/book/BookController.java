package com.example.first.book;

import com.example.first.utils.ApiResponse;
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
    public ApiResponse getAllBooks() {
        return new ApiResponse(true, bookService.getBooks(), "All books fetched",  200);
    }

    @GetMapping("/book")
    public ApiResponse getBookByTitle(@RequestParam String title){
        return new ApiResponse(true, bookService.getBookByTitle(title), "Book fetched", 200);
    }

    @GetMapping("/{id}")
    public ApiResponse getSingleBook(@PathVariable long id) {
        return new ApiResponse(true, bookService.getBookById(id), "Book fetched",  200);
    }



}
