package com.bookshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {
    @NotNull(message = "L'ID du livre est obligatoire")
    private Long bookId;

    @Min(value = 1, message = "La quantité doit être au moins 1")
    private int quantity;
}