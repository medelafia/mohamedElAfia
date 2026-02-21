package com.bookshop.service.impl;

import com.bookshop.dto.CartResponse;
import com.bookshop.entities.Book;
import com.bookshop.entities.CartItem;
import com.bookshop.entities.User;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CartItemRepository;
import com.bookshop.repository.UserRepository;
import com.bookshop.service.Impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private User sampleUser;
    private Book sampleBook;
    private CartItem sampleCartItem;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setEmail("user@bookshop.com");

        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Spring Boot Pro");
        sampleBook.setPrice(new BigDecimal("100.00"));
        sampleBook.setStock(10);

        sampleCartItem = new CartItem();
        sampleCartItem.setId(1L);
        sampleCartItem.setUser(sampleUser);
        sampleCartItem.setBook(sampleBook);
        sampleCartItem.setQuantity(2);
        sampleCartItem.setUnitPrice(new BigDecimal("100.00"));
    }

    @Test
    void testGetCart_ShouldCalculateTotalAmountCorrectly() {
        when(userRepository.findByEmail("user@bookshop.com")).thenReturn(Optional.of(sampleUser));
        when(cartItemRepository.findByUserId(1L)).thenReturn(Arrays.asList(sampleCartItem));

        CartResponse response = cartService.getCart("user@bookshop.com");

        assertNotNull(response);
        assertEquals(1, response.getItems().size());
        assertEquals(new BigDecimal("200.00"), response.getTotalAmount());
    }

    @Test
    void testAddToCart_WhenStockIsSufficient_ShouldSaveItem() {
        when(userRepository.findByEmail("user@bookshop.com")).thenReturn(Optional.of(sampleUser));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        when(cartItemRepository.findByUserIdAndBookId(1L, 1L)).thenReturn(Optional.empty());

        cartService.addToCart("user@bookshop.com", 1L, 3);

        ArgumentCaptor<CartItem> cartItemCaptor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemRepository, times(1)).save(cartItemCaptor.capture());

        CartItem savedItem = cartItemCaptor.getValue();
        assertEquals(3, savedItem.getQuantity());
        assertEquals(new BigDecimal("100.00"), savedItem.getUnitPrice());
    }

    @Test
    void testAddToCart_WhenStockIsInsufficient_ShouldThrowException() {
        when(userRepository.findByEmail("user@bookshop.com")).thenReturn(Optional.of(sampleUser));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartService.addToCart("user@bookshop.com", 1L, 15);
        });

        assertEquals("Stock insuffisant", exception.getMessage());
        verify(cartItemRepository, never()).save(any(CartItem.class));
    }

    @Test
    void testRemoveFromCart_WhenUserIsOwner_ShouldDelete() {
        when(userRepository.findByEmail("user@bookshop.com")).thenReturn(Optional.of(sampleUser));
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(sampleCartItem));

        cartService.removeFromCart("user@bookshop.com", 1L);

        verify(cartItemRepository, times(1)).delete(sampleCartItem);
    }

    @Test
    void testRemoveFromCart_WhenUserIsNotOwner_ShouldThrowException() {
        User hacker = new User();
        hacker.setId(99L);
        hacker.setEmail("hacker@bookshop.com");

        when(userRepository.findByEmail("hacker@bookshop.com")).thenReturn(Optional.of(hacker));
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(sampleCartItem));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartService.removeFromCart("hacker@bookshop.com", 1L);
        });

        assertEquals("Accès refusé", exception.getMessage());
        verify(cartItemRepository, never()).delete(any());
    }
}