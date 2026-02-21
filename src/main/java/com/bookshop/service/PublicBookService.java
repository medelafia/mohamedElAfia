package com.bookshop.service;

import com.bookshop.entities.Book;
import com.bookshop.entities.Category;
import org.springframework.data.domain.Page;
import java.util.List;

public interface PublicBookService {
    List<Category> getAllCategories();
    Page<Book> getBooks(Long categoryId, String keyword, int page, int size, String sortBy, String sortDir);
    Book getBookById(Long id);
}