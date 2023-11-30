package com.example.first.order;

import com.example.first.authanduser.User;
import com.example.first.book.Book;
import com.example.first.cart.Cart;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "quantity")
    private int quantity;


    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "status")
    private String status;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public Order() {
    }

    public Order(Cart cart) {
        this.user = cart.getUser();
        this.book = cart.getBook();
        this.quantity = cart.getQuantity();
        this.totalPrice = cart.getQuantity() * cart.getBook().getPrice();
        this.status = "processing";
        this.updatedOn = LocalDateTime.now();
    }

    public Order(User user, Book book, int quantity, double totalPrice, LocalDateTime date, String status, LocalDateTime updatedOn) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
        this.status = status;
        this.updatedOn = updatedOn;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
