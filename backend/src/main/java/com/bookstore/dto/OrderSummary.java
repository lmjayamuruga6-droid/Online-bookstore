package com.bookstore.dto;
import com.bookstore.model.CartItem;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderSummary {
    private List<CartItem> items;
    private double total;
    private LocalDateTime orderDate;
    private String orderId;
}