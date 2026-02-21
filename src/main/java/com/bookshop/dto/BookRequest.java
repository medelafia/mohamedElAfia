package com.bookshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Min(0)
    private Integer stock;

    private String description;

    @NotNull
    private Long categoryId;
}