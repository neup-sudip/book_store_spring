package com.example.first.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class NewReviewDto {

    @Min(value = 0, message = "Invalid book id")
    private long bookId;

    @Min(value = 0, message = "Rating should be greater than 0")
    @Max(value = 5, message = "Rating should be less than 6")
    private int rating;

    private String comment;

    public NewReviewDto(long bookId, int rating, String comment) {
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
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
}
