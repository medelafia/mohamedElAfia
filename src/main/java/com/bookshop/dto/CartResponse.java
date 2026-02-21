package com.bookshop.dto;

import com.bookshop.entities.CartItem;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private List<CartItem> items;
    private BigDecimal totalAmount;

    public CartResponse(List<CartItem> items) {
        this.items = items;
        this.totalAmount = items.stream()
                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}