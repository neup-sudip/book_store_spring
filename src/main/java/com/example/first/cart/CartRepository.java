package com.example.first.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart WHERE user_id = :userId", nativeQuery = true)
    List<Cart> findAllBooksByUser(long userId);
}
