package com.bookshop.dto;

import com.bookshop.entities.CartItem;
import lombok.Data;
import java.util.List;

@Data
public class CartResponse {
    private List<CartItem> items;
    private double totalAmount;

    public CartResponse(List<CartItem> items) {
        this.items = items;
        this.totalAmount = items.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
    }
}