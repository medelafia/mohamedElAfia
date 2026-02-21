package com.bookshop.service;

import com.bookshop.entities.Book;
import com.bookshop.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PublicBookService {
    List<Category> getAllCategories();
    Page<Book> getBooks(Pageable pageable);
    Book getBookById(Long id);
}