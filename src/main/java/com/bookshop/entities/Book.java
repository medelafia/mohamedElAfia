package com.bookshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String description;

    private BigDecimal price;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}