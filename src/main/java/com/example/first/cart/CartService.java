package com.example.first.cart;

import com.example.first.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getCartByUserIdAndBookId(long bookId, long userId){
        return  cartRepository.findCartByUserIdAndBookId(userId, bookId);
    }

    public List<CartResDto> getBooksFromCart(long userId) {
        List<Cart> cartList = cartRepository.findAllBooksByUser(userId);
        List<CartResDto> cartResDtoList = new ArrayList<>();
        for (Cart cart : cartList) {
            cartResDtoList.add(new CartResDto(cart.getCartId(), cart.getBook(), cart.getQuantity()));
        }
        return cartResDtoList;
    }

    public CartResDto addBookToCart(Cart cart) {
        Cart newCart = cartRepository.save(cart);
        return new CartResDto(newCart.getCartId(), newCart.getBook(), newCart.getQuantity());
    }

    public ResponseEntity<ApiResponse> updateCart(long cartId, int quantity, long userId){
        Cart prevCart = cartRepository.findById(cartId).orElse(null);
        if (prevCart != null && prevCart.getUser().getUserId() == userId) {

            prevCart.setQuantity(quantity);
            Cart cart = cartRepository.save(prevCart);
            CartResDto cartResDto = new CartResDto(cart.getCartId(), cart.getBook(), cart.getQuantity());

            ApiResponse apiResponse = new ApiResponse(true, cartResDto, "Cart updated successfully", 200);
            return ResponseEntity.status(200).body(apiResponse);
        } else {
            ApiResponse apiResponse = new ApiResponse(false, null, "Error updating cart", 400);
            return ResponseEntity.status(400).body(apiResponse);
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
