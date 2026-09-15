package com.bookstore.service;
import com.bookstore.dto.OrderSummary;
import com.bookstore.exception.*;
import com.bookstore.model.*;
import com.bookstore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CartService {
    @Autowired private CartRepository cartRepo;
    @Autowired private BookRepository bookRepo;

    public List<CartItem> getCart(String userId) {
        return cartRepo.findByUserId(userId);
    }

    @Transactional
    public CartItem addToCart(String userId, Long bookId, int qty) {
        if(qty <=0) throw new IllegalArgumentException("Quantity must be > 0");
        Book book = bookRepo.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException("Book " + bookId + " not found"));
        if(book.getStock() < qty) throw new InsufficientStockException("Only " + book.getStock() + " left in stock");

        Optional<CartItem> existing = cartRepo.findByUserIdAndBookId(userId, bookId);
        if(existing.isPresent()) {
            CartItem item = existing.get();
            int newQty = item.getQuantity() + qty;
            if(book.getStock() < newQty) throw new InsufficientStockException("Only " + book.getStock() + " left");
            item.setQuantity(newQty);
            return cartRepo.save(item);
        }
        CartItem newItem = CartItem.builder().book(book).quantity(qty).userId(userId).build();
        return cartRepo.save(newItem);
    }

    @Transactional
    public CartItem updateQuantity(Long itemId, int qty) {
        if(qty <=0) throw new IllegalArgumentException("Quantity must be > 0");
        CartItem item = cartRepo.findById(itemId)
            .orElseThrow(() -> new BookNotFoundException("Cart item " + itemId + " not found"));
        if(item.getBook().getStock() < qty) throw new InsufficientStockException("Only " + item.getBook().getStock() + " left");
        item.setQuantity(qty);
        return cartRepo.save(item);
    }

    @Transactional
    public void removeItem(Long itemId) {
        cartRepo.deleteById(itemId);
    }

    @Transactional
    public OrderSummary checkout(String userId) {
        List<CartItem> items = cartRepo.findByUserId(userId);
        if(items.isEmpty()) throw new CartEmptyException("Cart is empty, add books first");
        double total = items.stream().mapToDouble(i -> i.getBook().getPrice() * i.getQuantity()).sum();

        // Reduce stock
        for(CartItem item : items) {
            Book b = item.getBook();
            b.setStock(b.getStock() - item.getQuantity());
            bookRepo.save(b);
        }

        OrderSummary summary = OrderSummary.builder()
            .items(new ArrayList<>(items))
            .total(total)
            .orderDate(LocalDateTime.now())
            .orderId(UUID.randomUUID().toString())
            .build();

        cartRepo.deleteByUserId(userId);
        return summary;
    }
}