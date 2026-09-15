package com.bookstore.controller;
import com.bookstore.dto.OrderSummary;
import com.bookstore.model.CartItem;
import com.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/cart") @CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    @Autowired private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/{userId}/add/{bookId}")
    public ResponseEntity<CartItem> add(@PathVariable String userId, @PathVariable Long bookId,
                                        @RequestParam(defaultValue = "1") int quantity) {
        return new ResponseEntity<>(cartService.addToCart(userId, bookId, quantity), HttpStatus.CREATED);
    }

    @PutMapping("/update/{itemId}")
    public ResponseEntity<CartItem> update(@PathVariable Long itemId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(itemId, quantity));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Void> remove(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<OrderSummary> checkout(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.checkout(userId));
    }
}