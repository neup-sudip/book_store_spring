package com.example.first.book;

import com.example.first.review.ReviewDto;
import com.example.first.review.ReviewService;
import jakarta.persistence.Column;

import java.util.List;

public class BookResDto {

    private long bookId;
    private String title;
    private String slug;
    private String detail;
    private String author;
    private String genre;
    private double price;
    private boolean available;
    private double overallRating;
    private long numberOfReviews;

    private List<ReviewDto> reviews;
    public BookResDto() {
    }

    public BookResDto(Book book, double overallRating, long numberOfReviews) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.slug = book.getSlug();
        this.detail = book.getDetail();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.price = book.getPrice();
        this.available = book.isAvailable();
        this.overallRating = overallRating;
        this.numberOfReviews = numberOfReviews;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public long getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(long numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
