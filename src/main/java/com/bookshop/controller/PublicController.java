package com.bookshop.controller;

import com.bookshop.entities.Book;
import com.bookshop.entities.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Public API", description = "Endpoints publics accessibles sans authentification")
public interface PublicController {

    @Operation(
            summary = "Lister toutes les catégories",
            description = "Retourne la liste complète des catégories de livres disponibles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Succès"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    @GetMapping("/api/public/categories")
    ResponseEntity<List<Category>> getCategories();

    @Operation(
            summary = "Lister les livres avec pagination",
            description = "Retourne une page de livres. Permet la pagination via 'page' et 'size'.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Succès"),
                    @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    @GetMapping("/api/public/books")
    ResponseEntity<Page<Book>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

    @Operation(
            summary = "Détails d'un livre",
            description = "Retourne les informations détaillées d'un livre spécifique via son ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Succès"),
                    @ApiResponse(responseCode = "404", description = "Livre non trouvé"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    @GetMapping("/api/public/books/{id}")
    ResponseEntity<Book> getBookDetail(@PathVariable("id") Long id);
}