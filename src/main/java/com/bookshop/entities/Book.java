package com.bookshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String description;

    private double price;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void setPrice(@NotNull @Positive Double price) {

    }
}