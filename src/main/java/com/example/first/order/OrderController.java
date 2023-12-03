package com.example.first.order;

import com.example.first.authanduser.User;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ApiResponse placeOrder(HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");
        try {
            return orderService.placeOrder(decodedUser.getUserId());
        } catch (Exception exception) {
            return new ApiResponse(false, null, "Error placing orders ", 500);
        }
    }

    @GetMapping()
    public ApiResponse getOrdersByUser(HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");
        List<Order> orderList = orderService.getOrdersByUser(decodedUser.getUserId());
        return new ApiResponse(true, orderList, "Orders fetched", 200);
    }
}
