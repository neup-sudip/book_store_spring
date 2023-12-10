package com.example.first.order;

import com.example.first.cart.Cart;
import com.example.first.cart.CartRepository;
import com.example.first.utils.ApiResponse;
import com.example.first.utils.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public List<Order> getOrdersByUser(long userId) {
        return orderRepository.getOrdersByUser(userId);
    }

    public List<Order> getAllOrders(String sortBy, int orderBy) {
        try {
            if (orderBy == 1) {
                return orderRepository.findAll(Sort.by(sortBy).ascending());
            } else {
                return orderRepository.findAll(Sort.by(sortBy).descending());
            }
        } catch (Exception exception) {
            throw new CustomException("Error fetching order");
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse> placeOrder(long userId) {
        List<Cart> cartList = cartRepository.findAllBooksByUser(userId);

        if (cartList == null) {
            ApiResponse apiResponse = new ApiResponse(false, null, "No item to place order");
            return ResponseEntity.status(400).body(apiResponse);
        } else {
            List<Order> orderList = new ArrayList<>();
            for (Cart cart : cartList) {
                Order order = new Order(cart);
                orderList.add(order);
            }
            try {
                orderRepository.saveAll(orderList);
                cartRepository.deleteAll(cartList);
                ApiResponse apiResponse = new ApiResponse(true, null, "Order Placed successfully");
                return ResponseEntity.status(200).body(apiResponse);
            } catch (DataAccessException exception) {
                ApiResponse apiResponse = new ApiResponse(false, null, "Error placing order");
                return ResponseEntity.status(500).body(apiResponse);
            }
        }
    }

    public ResponseEntity<ApiResponse> updateOrderStatus(long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            ApiResponse apiResponse  = new ApiResponse(false, null, "Can not find order at the moment");
            return ResponseEntity.status(400).body(apiResponse);
        } else {
            order.setStatus(status);
            order.setUpdatedOn(LocalDateTime.now());
            orderRepository.save(order);
            ApiResponse apiResponse = new ApiResponse(true, null, "Order updated successfully");
            return ResponseEntity.status(200).body(apiResponse);
        }
    }


}
