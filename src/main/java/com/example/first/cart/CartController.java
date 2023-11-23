package com.example.first.cart;

import com.example.first.user.User;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public ApiResponse getAllBooks(HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        return new ApiResponse(true, cartService.getBooksFromCart(user.getUserId()), "Cart fetched successfully", 200);
    }

//    @PostMapping()
//    public ApiResponse addBookToCart(HttpServletRequest request, @RequestBody long bookId){
//        User user = (User) request.getAttribute("user");
//        Cart newCart = new Cart();
//        newCart.setQuantity(1);
//        newCart.setBookId(bookId);
//        newCart.setUserId(user.getUserId());
//        return new ApiResponse(true, cartService.addBookToCart(newCart), "Book added to cart", 200);
//    }

}
