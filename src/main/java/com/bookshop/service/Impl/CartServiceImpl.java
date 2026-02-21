package com.bookshop.service.Impl;

import com.bookshop.dto.CartResponse;
import com.bookshop.entities.Book;
import com.bookshop.entities.CartItem;
import com.bookshop.entities.User;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CartItemRepository;
import com.bookshop.repository.UserRepository;
import com.bookshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public CartResponse getCart(String userEmail) {
        User user = getUserByEmail(userEmail);
        List<CartItem> items = cartItemRepository.findByUserId(user.getId());
        return new CartResponse(items);
    }

    @Override
    @Transactional
    public void addToCart(String userEmail, Long bookId, int quantity) {
        User user = getUserByEmail(userEmail);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        if (book.getStock() < quantity) {
            throw new RuntimeException("Stock insuffisant");
        }

        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndBookId(user.getId(), bookId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            if (book.getStock() < newQuantity) {
                throw new RuntimeException("Stock insuffisant pour cette quantité totale");
            }
            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setBook(book);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(book.getPrice());
            cartItemRepository.save(newItem);
        }
    }

    @Override
    @Transactional
    public void updateCartItem(String userEmail, Long itemId, int quantity) {
        User user = getUserByEmail(userEmail);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Article introuvable dans le panier"));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Accès refusé");
        }

        if (item.getBook().getStock() < quantity) {
            throw new RuntimeException("Stock insuffisant");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void removeFromCart(String userEmail, Long itemId) {
        User user = getUserByEmail(userEmail);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Article introuvable"));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Accès refusé");
        }

        cartItemRepository.delete(item);
    }
}