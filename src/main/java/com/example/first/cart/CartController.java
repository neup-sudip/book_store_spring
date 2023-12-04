package com.example.first.cart;

import com.example.first.book.Book;
import com.example.first.authanduser.User;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllBooks(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse(true, cartService.getBooksFromCart(user.getUserId()), "Cart fetched successfully", 200);
        return ResponseEntity.status(200).body(apiResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBookToCart(@RequestBody long bookId, HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");
        if (cartService.getCartByUserIdAndBookId(bookId, decodedUser.getUserId()) != null) {
            ApiResponse apiResponse = new ApiResponse(false, null, "Book already in cart", 400);
            return ResponseEntity.status(400).body(apiResponse);
        } else {
            Cart newCart = new Cart();
            Book book = new Book();
            User user = new User();

            book.setBookId(bookId);
            user.setUserId(decodedUser.getUserId());
            newCart.setQuantity(1);
            newCart.setBook(book);
            newCart.setUser(user);
            ApiResponse apiResponse = new ApiResponse(true, cartService.addBookToCart(newCart), "Book added to cart", 200);
            return ResponseEntity.status(200).body(apiResponse);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse> editCart(@PathVariable long id, @RequestBody int quantity, HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");
        return cartService.updateCart(id, quantity, decodedUser.getUserId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBookFromCart(@PathVariable long id, HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");
        boolean success = cartService.removeBookFromCart(id, decodedUser.getUserId());
        if (success) {
            ApiResponse apiResponse = new ApiResponse(true, null, "Book removed from cart", 200);
            return ResponseEntity.status(200).body(apiResponse);
        } else {
            ApiResponse apiResponse = new ApiResponse(false, null, "Error removing book from cart", 400);
            return ResponseEntity.status(400).body(apiResponse);
        }
    }


}
