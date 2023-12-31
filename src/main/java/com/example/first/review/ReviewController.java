package com.example.first.review;

import com.example.first.authanduser.User;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

//    @GetMapping("/{id}")
//    public ApiResponse getAllReviews(@PathVariable long id){
//        return new ApiResponse(true, reviewService.getAllReviewsByBook(id), "Reviews fetched ", 200);
//    }
//
//    @GetMapping("/get/{id}")
//    public ApiResponse getSingleReview(@PathVariable long id){
//        ReviewDto reviewDto = reviewService.getSingleReview(id);
//        if(reviewDto == null){
//            return new ApiResponse(false, null, "Error fetching review ", 400);
//        }else{
//            return new ApiResponse(true, reviewDto, "Review fetched ", 200);
//        }
//    }

    @PostMapping()
    public ApiResponse addReview(@Valid @RequestBody NewReviewDto newReviewDto, HttpServletRequest request){
        User decodedUser = (User) request.getAttribute("user");

        Review prevReview = reviewService.getReviewByUserIdAndBookId(decodedUser.getUserId(), newReviewDto.getBookId());

        if(prevReview == null){
            ReviewDto newReview = reviewService.addReview(newReviewDto, decodedUser);
            if(newReview == null){
                return new ApiResponse(false, null, "Error adding review");
            }else{
                return new ApiResponse(true, newReview, "Review added");
            }
        }else{
            return reviewService.editReview(newReviewDto, decodedUser.getUserId(), prevReview.getReviewId());
        }
    }

//    @PutMapping("/{id}")
//    public ApiResponse editReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable long id,  HttpServletRequest request){
//        User decodedUser = (User) request.getAttribute("user");
//        return reviewService.editReview(reviewDto, decodedUser.getUserId(), id);
//    }

}
