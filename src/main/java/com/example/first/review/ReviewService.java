package com.example.first.review;

import com.example.first.authanduser.User;
import com.example.first.book.Book;
import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDto> getAllReviews(long bookId){
        List<Review> reviewList = reviewRepository.getReviewByBookId(bookId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for(Review review : reviewList){
            ReviewUserDto reviewUserDto = new ReviewUserDto(review.getUser().getUserId(), review.getUser().getUsername());
            ReviewDto reviewDto = new ReviewDto(review.getReviewId(), review.getBook().getBookId(), review.getRating(), review.getComment(), reviewUserDto);
            reviewDtoList.add(reviewDto);
        }

        return reviewDtoList;
    }

    public ReviewDto getSingleReview(long reviewId){
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(review == null){
            return null;
        }else{
            ReviewUserDto reviewUserDto = new ReviewUserDto(review.getUser().getUserId(), review.getUser().getUsername());
            return new ReviewDto(review.getReviewId(), review.getBook().getBookId(), review.getRating(), review.getComment(), reviewUserDto);
        }
    }

    public ReviewDto addReview(ReviewDto reviewDto, long userId){
        Book book = new Book();
        book.setBookId(reviewDto.getBookId());

        User user = new User();
        user.setUserId(userId);

        Review newReview = new Review(user, book, reviewDto.getRating(), reviewDto.getComment());

        try{
            Review review = reviewRepository.save(newReview);
            ReviewUserDto reviewUserDto = new ReviewUserDto(review.getUser().getUserId(), review.getUser().getUsername());
            return new ReviewDto(review.getReviewId(), review.getBook().getBookId(), review.getRating(), review.getComment(), reviewUserDto);
        }catch (Exception exception){
            return null;
        }
    }

    public ApiResponse editReview(ReviewDto reviewDto, long userId, long reviewId){
        Review prevReview = reviewRepository.findById(reviewId).orElse(null);

        if(prevReview == null){
            return new ApiResponse(false, null,"Can not found review at the moment ", 400);
        }else{
            if(prevReview.getUser().getUserId() != userId){
                return new ApiResponse(false, null,"Error updating review", 400);
            }else{
                prevReview.setRating(reviewDto.getRating());
                prevReview.setComment(reviewDto.getComment());

                reviewRepository.save(prevReview);

                ReviewUserDto reviewUserDto = new ReviewUserDto(prevReview.getUser().getUserId(), prevReview.getUser().getUsername());
                ReviewDto reviewDto1 = new ReviewDto(prevReview.getReviewId(), prevReview.getBook().getBookId(), prevReview.getRating(), prevReview.getComment(), reviewUserDto);
                return new ApiResponse(true, reviewDto1, "Successfully updated review", 200);
            }
        }

    }
}
