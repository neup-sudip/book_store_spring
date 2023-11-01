package com.example.first.book;

import com.example.first.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseWrapper getAllBooks(){
        return new ResponseWrapper(bookRepository.findAll(), 200, "All books fetched ");
    }

    public ResponseWrapper getBookById(@PathVariable long id) {
        boolean exists = bookRepository.existsById(id);
        if(exists){
            return new ResponseWrapper(bookRepository.findById(id), 200, "Book fetched !");
        }else {
            return new ResponseWrapper(null, 404, "Book not found fetched !");
        }
    }

    public ResponseWrapper getBookByTitle (String title){
        Book book = bookRepository.findByTitle(title);
        if(book == null){
            return new ResponseWrapper(null, 404, "Book not found fetched !");
        }else{
            return new ResponseWrapper(book, 200, "Successfully fetched !");
        }
    }

    public ResponseWrapper addBook(@RequestBody Book book) {
        return new ResponseWrapper(book, 200, "Book added !");
    }

    public ResponseWrapper editBook(@PathVariable long id, @RequestBody Book newBook) {
        boolean exists = bookRepository.existsById(id);

        if (exists) {
            Book book = bookRepository.findById(id).orElse(null);
            book.setAuthor(newBook.getAuthor());
            book.setGenre(newBook.getGenre());
            book.setTitle(newBook.getTitle());
            book.setPrice(newBook.getPrice());

            return new ResponseWrapper(book, 200, "Book updated !");
        } else {
            return new ResponseWrapper(null, 404, "Book not found fetched !");
        }
    }

    public ResponseWrapper deleteBook(@PathVariable long id){
        boolean exists = bookRepository.existsById(id);

        if(exists){
            bookRepository.deleteById(id);
            return new ResponseWrapper(null, 200, "Book deleted !");
        }else{
            return new ResponseWrapper(null, 404, "Book not found fetched !");
        }
    }
}
