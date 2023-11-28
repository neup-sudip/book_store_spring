package com.example.first.book;

public class BookResDto {

    private Book book;

    private double overallRating;

    private int numberOfReviews;

    public BookResDto() {
    }

    public BookResDto(Book book, double overallRating, int numberOfReviews) {
        this.book = book;
        this.overallRating = overallRating;
        this.numberOfReviews = numberOfReviews;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }
}
