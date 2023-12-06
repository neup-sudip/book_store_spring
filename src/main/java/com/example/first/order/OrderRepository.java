package com.example.first.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE user_id = :userId ORDER BY date DESC", nativeQuery = true)
    List<Order> getOrdersByUser(long userId);
}
