package com.bookshop.dto;

import lombok.Data;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private String description;
    private String categoryName;
}