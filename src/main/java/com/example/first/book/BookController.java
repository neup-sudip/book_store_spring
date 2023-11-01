package com.example.first.book;

import com.example.first.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseWrapper getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book")
    public ResponseWrapper getBookByTitle(@RequestParam String title){
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/{id}")
    public ResponseWrapper getSingleBook(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @PostMapping()
    public ResponseWrapper addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public ResponseWrapper editBook(@PathVariable long id, @RequestBody Book newBook) {
        return  bookService.editBook(id, newBook);
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper deleteBook(@PathVariable long id){
       return bookService.deleteBook(id);
    }
}
