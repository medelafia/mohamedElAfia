package com.bookshop.controller.Impl;

import com.bookshop.controller.CartController;
import com.bookshop.dto.CartItemRequest;
import com.bookshop.dto.CartResponse;
import com.bookshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {

    private final CartService cartService;

    @Override
    public ResponseEntity<CartResponse> getCart(Principal principal) {
        return ResponseEntity.ok(cartService.getCart(principal.getName()));
    }

    @Override
    public ResponseEntity<Void> addToCart(Principal principal, CartItemRequest request) {
        cartService.addToCart(principal.getName(), request.getBookId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateCartItem(Principal principal, Long itemId, int quantity) {
        cartService.updateCartItem(principal.getName(), itemId, quantity);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeFromCart(Principal principal, Long itemId) {
        cartService.removeFromCart(principal.getName(), itemId);
        return ResponseEntity.ok().build();
    }
}