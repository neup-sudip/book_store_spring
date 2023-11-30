package com.example.first.review;

import com.example.first.authanduser.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Date;

public class ReviewDto {

    private long reviewId;

    private long bookId;

    private int rating;

    private String comment;

    private String username;

    private long userId;

    private LocalDateTime date;

    public ReviewDto() {
    }

    public ReviewDto(Review review) {
        this.reviewId = review.getReviewId();
        this.bookId = review.getBook().getBookId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.userId = review.getUser().getUserId();
        this.username = review.getUser().getUsername();
        this.date = review.getDate();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
