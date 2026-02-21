package com.bookshop.controller;

import com.bookshop.dto.BookRequest;
import com.bookshop.dto.BookResponse;
import com.bookshop.exception.ResourceNotFoundException;
import com.bookshop.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private BookRequest bookRequest;
    private BookResponse bookResponse;

    @BeforeEach
    void setUp() {
        bookRequest = new BookRequest();
        bookRequest.setTitle("Clean Code");
        bookRequest.setAuthor("Robert Martin");
        bookRequest.setPrice(29.99);
        bookRequest.setStock(10);
        bookRequest.setDescription("Un livre sur le code propre");
        bookRequest.setCategoryId(1L);

        bookResponse = new BookResponse();
        bookResponse.setId(1L);
        bookResponse.setTitle("Clean Code");
        bookResponse.setAuthor("Robert Martin");
        bookResponse.setPrice(29.99);
        bookResponse.setStock(10);
        bookResponse.setCategoryName("Roman");
    }

    @Test
    void addBook_Success() {
        when(adminService.addBook(any(BookRequest.class))).thenReturn(bookResponse);

        ResponseEntity<BookResponse> result = adminController.addBook(bookRequest);

        assertEquals(201, result.getStatusCode().value());
        assertEquals("Clean Code", result.getBody().getTitle());
        assertEquals("Roman", result.getBody().getCategoryName());
    }

    @Test
    void addBook_CategoryNotFound_ThrowsException() {
        when(adminService.addBook(any(BookRequest.class)))
                .thenThrow(new ResourceNotFoundException("Catégorie non trouvée"));

        assertThrows(ResourceNotFoundException.class,
                () -> adminController.addBook(bookRequest));
    }

    @Test
    void deleteBook_Success() {
        doNothing().when(adminService).deleteBook(1L);

        ResponseEntity<?> result = adminController.deleteBook(1L);

        assertEquals(200, result.getStatusCode().value());
        verify(adminService, times(1)).deleteBook(1L);
    }

    @Test
    void deleteBook_NotFound_ThrowsException() {
        doThrow(new ResourceNotFoundException("Livre non trouvé"))
                .when(adminService).deleteBook(1L);

        assertThrows(ResourceNotFoundException.class,
                () -> adminController.deleteBook(1L));
    }
}