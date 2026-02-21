package com.bookshop.service;

import com.bookshop.dto.BookRequest;
import com.bookshop.dto.BookResponse;
import com.bookshop.entities.Book;
import com.bookshop.entities.Category;
import com.bookshop.exception.ResourceNotFoundException;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public AdminService(BookRepository bookRepository,
                        CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public BookResponse addBook(BookRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée"));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        book.setDescription(request.getDescription());
        book.setCategory(category);

        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livre non trouvé"));
        bookRepository.delete(book);
    }

    private BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setPrice(book.getPrice());
        response.setStock(book.getStock());
        response.setDescription(book.getDescription());
        response.setCategoryName(book.getCategory().getName());
        return response;
    }
}