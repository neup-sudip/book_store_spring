package com.example.first.book;

import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ApiResponse searchBooks(@RequestParam(name = "query", defaultValue = "") String query,
                                   @RequestParam(name = "page", defaultValue = "1") int page) {

        List<Book> books = bookService.searchBooks(query, page);
        int totalPages = bookService.countBooks(query);
        BookPaginationRes bookPaginationRes = new BookPaginationRes(books, totalPages);

        return new ApiResponse(true, bookPaginationRes, "Books fetched successfully", 200);
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
