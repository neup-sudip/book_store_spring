package com.example.first.book;

import com.example.first.utils.ResponseData;
import com.example.first.utils.ResponseWrapper;
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
    public ResponseWrapper getAllBooks() {
        ResponseData res = new ResponseData(bookService.getBooks(), "All books fetched", true);
        return new ResponseWrapper(res, 200);
    }

    @GetMapping("/book")
    public ResponseWrapper getBookByTitle(@RequestParam String title){
        ResponseData res = new ResponseData(bookService.getBookByTitle(title), "Book fetched", true);
        return new ResponseWrapper(res, 200);
    }

    @GetMapping("/{id}")
    public ResponseWrapper getSingleBook(@PathVariable long id) {
        ResponseData res = new ResponseData(bookService.getBookById(id), "Book fetched", true);
        return new ResponseWrapper(res, 200);
    }



}
