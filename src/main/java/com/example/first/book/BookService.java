package com.example.first.book;

import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll(Sort.by("bookId"));
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book getBookByTitle(String bookTitle){
        return bookRepository.findByTitle(bookTitle);
    }

    public Book addNewBook(Book book) {
        return bookRepository.save(book);
    }

    public ApiResponse updateBook(Book book, long id) {
        Book prevBook = bookRepository.findById(id).orElse(null);

        if (prevBook == null) {
            return new ApiResponse(false, null, "Book not found !",  400);
        }

        prevBook.setTitle(book.getTitle());
        prevBook.setAuthor(book.getAuthor());
        prevBook.setGenre(book.getGenre());
        prevBook.setPrice(book.getPrice());
        prevBook.setAvailable(book.isAvailable());
        bookRepository.save(prevBook);

        return new ApiResponse(true, prevBook, "Book updated successfully !",  200);
    }
}
