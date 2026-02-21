package com.bookshop.controller.Impl;

import com.bookshop.controller.PublicController;
import com.bookshop.entities.Book;
import com.bookshop.entities.Category;
import com.bookshop.service.PublicBookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublicControllerImpl implements PublicController {

    private final PublicBookService publicBookService;

    public PublicControllerImpl(PublicBookService publicBookService) {
        this.publicBookService = publicBookService;
    }

    @Override
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = publicBookService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @Override
    public ResponseEntity<Page<Book>> getBooks(Long categoryId, String keyword, int page, int size, String sortBy, String sortDir) {
        Page<Book> booksPage = publicBookService.getBooks(categoryId, keyword, page, size, sortBy, sortDir);
        return ResponseEntity.ok().body(booksPage);
    }

    @Override
    public ResponseEntity<Book> getBookDetail(Long id) {
        Book book = publicBookService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }
}