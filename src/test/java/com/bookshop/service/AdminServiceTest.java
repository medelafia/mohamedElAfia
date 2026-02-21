package com.bookshop.service;

import com.bookshop.dto.BookRequest;
import com.bookshop.dto.BookResponse;
import com.bookshop.entities.Book;
import com.bookshop.entities.Category;
import com.bookshop.exception.ResourceNotFoundException;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private AdminService adminService;

    private BookRequest bookRequest;
    private Category category;
    private Book book;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Roman");

        bookRequest = new BookRequest();
        bookRequest.setTitle("Clean Code");
        bookRequest.setAuthor("Robert Martin");
        bookRequest.setPrice(29.99);
        bookRequest.setStock(10);
        bookRequest.setDescription("Un livre sur le code propre");
        bookRequest.setCategoryId(1L);

        book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setPrice(29.99);
        book.setStock(10);
        book.setCategory(category);
    }

    @Test
    void addBook_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponse response = adminService.addBook(bookRequest);

        assertNotNull(response);
        assertEquals("Clean Code", response.getTitle());
        assertEquals("Roman", response.getCategoryName());
    }

    @Test
    void addBook_CategoryNotFound_ThrowsException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> adminService.addBook(bookRequest));
    }

    @Test
    void deleteBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        assertDoesNotThrow(() -> adminService.deleteBook(1L));
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBook_NotFound_ThrowsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> adminService.deleteBook(1L));
    }
}