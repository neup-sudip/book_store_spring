package com.example.first.cart;

import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getBooksFromCart(long userId) {
        return cartRepository.findAllBooksByUser(userId);
    }

    public Cart addBookToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public ApiResponse updateCart(long cartId, int quantity, long userId){
        Cart prevCart = cartRepository.findById(cartId).orElse(null);
        if (prevCart != null && prevCart.getUser().getUserId() == userId) {
            prevCart.setQuantity(quantity);
            cartRepository.save(prevCart);
            return new ApiResponse(true, prevCart, "Cart updated successfully", 200);
        } else {
            return new ApiResponse(false, null, "Error updating cart", 400);
        }
    }

    public boolean removeBookFromCart(long cartId, long userId) {
        try {
            Cart cart = cartRepository.findById(cartId).orElse(null);
            if (cart != null && cart.getUser().getUserId() == userId) {
                cartRepository.deleteById(cartId);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
