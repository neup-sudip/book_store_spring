package com.example.first.book;

import com.example.first.review.ReviewDto;
import com.example.first.review.ReviewService;
import com.example.first.utils.ApiResponse;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    private final ReviewService reviewService;

    @Autowired
    public BookController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping()
    public ApiResponse getBooks(@RequestParam(name = "query", defaultValue = "") String query,
                                   @RequestParam(name = "page", defaultValue = "1") int page) {

        List<Book> books = bookService.getBooks(query, page);
        int totalPages = bookService.countBooks(query);

        List<Long> bookIds = new ArrayList<>();
        books.forEach(book -> {
            bookIds.add(book.getBookId());
        });

       List<Map<String, Object>> ratings = reviewService.getRatingOfBooks(bookIds);

       List<BookResDto> bookResDtos = new ArrayList<>();

        for (Book book : books) {
            for (Map<String, Object> rating : ratings) {
                Long bookId = (Long) rating.get("book_id");
                if (book.getBookId() == (bookId)) {
                    float overallRating = Float.parseFloat(rating.get("overall_rating").toString());
                    long numRatings = Long.parseLong(rating.get("num_reviews").toString());
                    BookResDto bookResDto = new BookResDto( book, overallRating, numRatings);
                    bookResDtos.add(bookResDto);
                    break;
                }else{
                    BookResDto bookResDto = new BookResDto( book, 0, 0);
                    bookResDtos.add(bookResDto);
                }
            }
        }

        BookPaginationRes bookPaginationRes = new BookPaginationRes(bookResDtos, totalPages);

        return new ApiResponse(true, bookPaginationRes, "Books fetched successfully", 200);
    }

    @GetMapping("/{slug}")
    public ApiResponse getBookBySlug(@PathVariable String slug) {
        Book book = bookService.getBookBySlug(slug);
        if (book == null) {
            return new ApiResponse(false, null, "Book not found", 200);
        } else {
            Map<String, Object> rating = reviewService.getSingleBookRating(book.getBookId());
            float overallRating = Float.parseFloat(rating.get("overall_rating").toString());
            long numRatings = Long.parseLong(rating.get("num_reviews").toString());
            BookResDto bookResDto = new BookResDto(book, overallRating,numRatings);
            return new ApiResponse(true, bookResDto, "Book fetched", 200);
        }
    }
}
