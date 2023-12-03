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

    @GetMapping()
    public ApiResponse getAllOrders(@RequestParam(name = "sort", defaultValue = "date") String sort,
                                    @RequestParam(name = "order", defaultValue = "1") int order) {
        return new ApiResponse(true, orderService.getAllOrders(sort, order), "All orders fetched", 200);
    }

    @PutMapping("/{orderId}")
    public ApiResponse updateOrderStatus(@PathVariable long orderId, @RequestBody String status) {
        String statusStr = status.replaceAll("\"", "");
        return orderService.updateOrderStatus(orderId, statusStr);
    }
}
