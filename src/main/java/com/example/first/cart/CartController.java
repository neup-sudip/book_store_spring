package com.example.first.cart;

import com.example.first.book.Book;
import com.example.first.user.User;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
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

    @PostMapping("/add")
    public ApiResponse addBookToCart(@RequestBody long bookId, HttpServletRequest request){
        System.out.println("---");
        Cart newCart = new Cart();
        Book book = new Book();
        User user = new User();

        User decodedUser = (User) request.getAttribute("user");
        book.setBookId(bookId);
        user.setUserId(decodedUser.getUserId());

        newCart.setQuantity(1);
        newCart.setBookId(book);
        newCart.setUserId(user);
        return new ApiResponse(true, cartService.addBookToCart(newCart), "Book added to cart", 200);
    }

}
