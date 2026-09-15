package com.bookstore.repository;
import com.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(String userId);
    Optional<CartItem> findByUserIdAndBookId(String userId, Long bookId);
    void deleteByUserId(String userId);
}