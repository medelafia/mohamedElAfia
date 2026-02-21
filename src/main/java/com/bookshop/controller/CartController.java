package com.bookshop.controller;

import com.bookshop.dto.CartItemRequest;
import com.bookshop.dto.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Cart API", description = "Gestion du panier (Nécessite JWT)")
@SecurityRequirement(name = "Bearer Authentication")
    @RequestMapping("/api/cart")
public interface CartController {

    @Operation(summary = "Voir le panier")
    @GetMapping
    ResponseEntity<CartResponse> getCart(Principal principal);

    @Operation(summary = "Ajouter un livre au panier")
    @PostMapping("/items")
    ResponseEntity<Void> addToCart(Principal principal, @Valid @RequestBody CartItemRequest request);

    @Operation(summary = "Modifier la quantité d'un article")
    @PutMapping("/items/{itemId}")
    ResponseEntity<Void> updateCartItem(Principal principal, @PathVariable Long itemId, @RequestParam int quantity);

    @Operation(summary = "Supprimer un article du panier")
    @DeleteMapping("/items/{itemId}")
    ResponseEntity<Void> removeFromCart(Principal principal, @PathVariable Long itemId);
}