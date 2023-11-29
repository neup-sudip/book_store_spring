package com.example.first.book;

import java.util.List;

public class BookPaginationRes {

    private List<BookResDto> books;

    private int totalPages;

    public BookPaginationRes() {
    }

    public BookPaginationRes(List<BookResDto> books, int totalPages) {
        this.books = books;
        this.totalPages = totalPages;
    }

    public List<BookResDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookResDto> books) {
        this.books = books;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
