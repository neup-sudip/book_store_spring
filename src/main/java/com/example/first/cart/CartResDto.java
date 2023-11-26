package com.example.first.cart;

import com.example.first.book.Book;

public class CartResDto {

    private long cartId;

    private Book book;

    private int quantity;

    public CartResDto() {
    }

    public CartResDto(long cartId, Book book, int quantity) {
        this.cartId = cartId;
        this.book = book;
        this.quantity = quantity;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
