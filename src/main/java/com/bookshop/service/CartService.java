package com.bookshop.service;

import com.bookshop.dto.CartResponse;

public interface CartService {
    CartResponse getCart(String userEmail);
    void addToCart(String userEmail, Long bookId, int quantity);
    void updateCartItem(String userEmail, Long itemId, int quantity);
    void removeFromCart(String userEmail, Long itemId);
}