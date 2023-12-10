package com.example.first.book;

import com.example.first.utils.ApiResponse;
import com.example.first.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final int totalBooksPerPage = 1;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookBySlug(String slug) {
        return bookRepository.findBySlug(slug);
    }

    public int countBooks(String searchTerm) {
        long totalBooks = bookRepository.countBooks(searchTerm);

        if (totalBooks <= 0) {
            return 1;
        } else {
            return (int) Math.ceil((double) totalBooks / totalBooksPerPage);
        }
    }

    public List<Book> getBooks(String searchTerm, int currentPage) {
        int offset = 0;
        if (currentPage > 1) {
            offset = (currentPage - 1) * totalBooksPerPage;
        }
        return bookRepository.searchBooks(searchTerm, offset, totalBooksPerPage);
    }

    public Book addNewBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (Exception exception) {
            throw new CustomException("Error while adding book !");
        }
    }

    public ResponseEntity<ApiResponse> updateBook(Book book, long id) {
        try{
            Book prevBook = bookRepository.findById(id).orElse(null);

            if (prevBook == null) {
                ApiResponse apiResponse = new ApiResponse(false, null, "Book not found !");
                return ResponseEntity.status(400).body(apiResponse);
            }

            prevBook.setTitle(book.getTitle());
            prevBook.setSlug(book.getSlug());
            prevBook.setDetail(book.getDetail());
            prevBook.setAuthor(book.getAuthor());
            prevBook.setGenre(book.getGenre());
            prevBook.setPrice(book.getPrice());
            prevBook.setAvailable(book.isAvailable());
            bookRepository.save(prevBook);

            ApiResponse apiResponse = new ApiResponse(true, prevBook, "Book updated successfully !");
            return ResponseEntity.status(200).body(apiResponse);
        }catch (Exception exception){
            throw new CustomException("Error while updating book !");
        }
    }
}
