package com.example.first.book;

import java.util.List;

public class BookPaginationRes {

    private List<Book> books;

    private int totalPages;

    public BookPaginationRes() {
    }

    public BookPaginationRes(List<Book> books, int totalPages) {
        this.books = books;
        this.totalPages = totalPages;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
