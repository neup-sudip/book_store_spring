package com.example.first.cart;

import com.example.first.book.Book;
import com.example.first.authanduser.User;
import com.example.first.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponse getAllBooks(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return new ApiResponse(true, cartService.getBooksFromCart(user.getUserId()), "Cart fetched successfully", 200);
    }

    @PostMapping("/add")
    public ApiResponse addBookToCart(@RequestBody long bookId, HttpServletRequest request) {
        Cart newCart = new Cart();
        Book book = new Book();
        User user = new User();

        User decodedUser = (User) request.getAttribute("user");
        book.setBookId(bookId);
        user.setUserId(decodedUser.getUserId());

        newCart.setQuantity(1);
        newCart.setBook(book);
        newCart.setUser(user);
        return new ApiResponse(true, cartService.addBookToCart(newCart), "Book added to cart", 200);
    }

    @PutMapping("/edit/{id}")
    public ApiResponse editCart(@PathVariable long id, @RequestBody int quantity,  HttpServletRequest request) {
        User decodedUser = (User) request.getAttribute("user");
        return cartService.updateCart(id, quantity, decodedUser.getUserId());
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteBookFromCart(@PathVariable long id, HttpServletRequest request)  {
        User decodedUser = (User) request.getAttribute("user");
        boolean success = cartService.removeBookFromCart(id, decodedUser.getUserId());
        if(success){
            return new ApiResponse(true, null, "Book removed from cart", 200);
        }else{
            return new ApiResponse(false, null, "Error removing book from cart", 400);
        }
    }


}
