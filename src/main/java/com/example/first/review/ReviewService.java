package com.example.first.review;

import com.example.first.authanduser.User;
import com.example.first.book.Book;
import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDto> getAllReviewsByBook(long bookId) {
        List<Review> reviewList = reviewRepository.getReviewByBookId(bookId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewUserDto reviewUserDto = new ReviewUserDto(review.getUser().getUserId(), review.getUser().getUsername());
            ReviewDto reviewDto = new ReviewDto(review.getBook().getBookId(), review.getRating(), review.getComment(), reviewUserDto);
            reviewDto.setReviewId(review.getReviewId());
            reviewDtoList.add(reviewDto);
        }

        return reviewDtoList;
    }

    public Review getReviewByUserId(long userId){
        return reviewRepository.getReviewByUserId(userId);
    }

    public ReviewDto getSingleReview(long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {
            return null;
        } else {
            ReviewUserDto reviewUserDto = new ReviewUserDto(review.getUser().getUserId(), review.getUser().getUsername());

            ReviewDto reviewDto = new ReviewDto(review.getBook().getBookId(), review.getRating(), review.getComment(), reviewUserDto);
            reviewDto.setReviewId(review.getReviewId());
            return reviewDto;
        }
    }

    public ReviewDto addReview(ReviewDto reviewDto, long userId) {
        Book book = new Book();
        book.setBookId(reviewDto.getBookId());

        User user = new User();
        user.setUserId(userId);

        Review newReview = new Review(user, book, reviewDto.getRating(), reviewDto.getComment());

        try {
            Review review = reviewRepository.save(newReview);
            ReviewUserDto reviewUserDto = new ReviewUserDto(review.getUser().getUserId(), review.getUser().getUsername());
            ReviewDto reviewDto1 = new ReviewDto(review.getBook().getBookId(), review.getRating(), review.getComment(), reviewUserDto);
            reviewDto1.setReviewId(review.getReviewId());
            return reviewDto1;
        } catch (Exception exception) {
            return null;
        }
    }

    public ApiResponse editReview(ReviewDto reviewDto, long userId, long reviewId) {
        Review prevReview = reviewRepository.findById(reviewId).orElse(null);

        if (prevReview == null) {
            return new ApiResponse(false, null, "Can not found review at the moment ", 400);
        } else {
            if (prevReview.getUser().getUserId() != userId) {
                return new ApiResponse(false, null, "Error updating review", 400);
            } else {
                prevReview.setRating(reviewDto.getRating());
                prevReview.setComment(reviewDto.getComment());

                reviewRepository.save(prevReview);

                ReviewUserDto reviewUserDto = new ReviewUserDto(prevReview.getUser().getUserId(), prevReview.getUser().getUsername());
                ReviewDto reviewDto1 = new ReviewDto(prevReview.getBook().getBookId(), prevReview.getRating(), prevReview.getComment(), reviewUserDto);
                reviewDto1.setReviewId(prevReview.getReviewId());
                return new ApiResponse(true, reviewDto1, "Successfully updated review", 200);
            }
        }

    }

    public Map<String, Object> getSingleBookRating(long bookId) {
        List<Map<String, Object>> reviews = reviewRepository.getOverallRatingAndNumReviewsForSingleBook(bookId);
        try {
            return reviews.get(0);
        } catch (IndexOutOfBoundsException ex) {
            Map<String, Object> emptyRating = new HashMap<>();
            emptyRating.put("book_id", bookId);
            emptyRating.put("overall_rating", 0);
            emptyRating.put("num_reviews", 0);
            return emptyRating;
        }
    }

    public List<Map<String, Object>> getRatingOfBooks(List<Long> bookIds) {
        return reviewRepository.getOverallRatingAndNumReviewsForBooks(bookIds);
    }
}
