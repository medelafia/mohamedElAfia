package com.bookshop.service;

import com.bookshop.entities.Book;
import com.bookshop.entities.Category;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.Impl.PublicBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicBookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private PublicBookServiceImpl publicBookService;

    private Book sampleBook;
    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        sampleCategory = new Category();
        sampleCategory.setId(1L);
        sampleCategory.setName("Programmation");

        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Spring Boot en Action");
        sampleBook.setAuthor("Craig Walls");
        sampleBook.setPrice(45.00);
        sampleBook.setStock(10);
        sampleBook.setCategory(sampleCategory);
    }

    @Test
    void testGetAllCategories_ShouldReturnList() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(sampleCategory));

        List<Category> result = publicBookService.getAllCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Programmation", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetBooks_ShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> bookPage = new PageImpl<>(Arrays.asList(sampleBook));
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        Page<Book> result = publicBookService.getBooks(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Spring Boot en Action", result.getContent().get(0).getTitle());
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetBookById_WhenBookExists_ShouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

        Book result = publicBookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Craig Walls", result.getAuthor());
    }

    @Test
    void testGetBookById_WhenBookDoesNotExist_ShouldThrowException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            publicBookService.getBookById(99L);
        });

        assertEquals("Livre non trouvé", exception.getMessage());
        verify(bookRepository, times(1)).findById(99L);
    }
}