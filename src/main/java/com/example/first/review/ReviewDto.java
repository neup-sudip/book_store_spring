package com.example.first.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ReviewDto {

    private long reviewId;

    @Min(value = 0, message = "Invalid book id")
    private long bookId;

    @Min(value = 0, message = "Rating should be greater than 0")
    @Max(value = 5, message = "Rating should be less than 6")
    private int rating;

    private String comment;

    private ReviewUserDto user;

    public ReviewDto() {
    }

    public ReviewDto(long bookId, int rating, String comment, ReviewUserDto reviewUserDto) {
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
        this.user = reviewUserDto;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewUserDto getReviewUserDto() {
        return user;
    }

    public void setReviewUserDto(ReviewUserDto reviewUserDto) {
        this.user = reviewUserDto;
    }
}
