package com.bookshop.controller;

import com.bookshop.dto.BookRequest;
import com.bookshop.dto.BookResponse;
import com.bookshop.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/books")
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.addBook(request));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        adminService.deleteBook(id);
        return ResponseEntity.ok().body("Livre supprimé avec succès");
    }
}

