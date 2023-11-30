package com.example.first.admin;

import com.example.first.order.OrderService;
import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ApiResponse getAllOrders(){
        return new ApiResponse(true, orderService.getAllOrders(), "All orders fetched", 200);
    }

    @PutMapping("/{orderId}")
    public ApiResponse updateOrderStatus(@PathVariable long orderId, @RequestBody String status){
        return  orderService.updateOrderStatus(orderId, status);
    }
}
