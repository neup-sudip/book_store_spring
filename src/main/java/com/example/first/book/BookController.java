package com.example.first.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping()
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getSingleBook(@PathVariable Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @PostMapping()
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @RequestBody Book newBook) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            book.setAuthor(newBook.getAuthor());
            book.setGenre(newBook.getGenre());
            book.setTitle(newBook.getTitle());
            book.setPrice(newBook.getPrice());

            return ResponseEntity.ok(bookRepository.save(book));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id){
        Book book = bookRepository.findById(id).orElse(null);

        if(book == null){
            return "Book with " + id + " not found";
        }else{
            bookRepository.deleteById(id);
            return "Successfully deleted";
        }
    }
}
