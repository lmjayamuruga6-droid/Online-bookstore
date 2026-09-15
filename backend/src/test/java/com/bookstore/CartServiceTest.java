package com.bookstore;
import com.bookstore.model.*;
import com.bookstore.repository.*;
import com.bookstore.service.CartService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @Transactional
class CartServiceTest {
    @Autowired CartService cartService;
    @Autowired BookRepository bookRepo;
    @Autowired CartRepository cartRepo;

    @Test
    void shouldAddBookToCart() {
        Book book = bookRepo.findAll().get(0);
        var item = cartService.addToCart("test-user", book.getId(), 1);
        assertNotNull(item);
        assertEquals(1, item.getQuantity());
    }

    @Test
    void shouldThrowWhenStockInsufficient() {
        Book book = bookRepo.findAll().get(0);
        assertThrows(Exception.class, () -> cartService.addToCart("test-user", book.getId(), 1000));
    }

    @Test
    void shouldCheckoutAndClearCart() {
        Book book = bookRepo.findAll().get(0);
        cartService.addToCart("checkout-user", book.getId(), 1);
        var summary = cartService.checkout("checkout-user");
        assertTrue(summary.getTotal() > 0);
        assertEquals(0, cartRepo.findByUserId("checkout-user").size());
    }
}