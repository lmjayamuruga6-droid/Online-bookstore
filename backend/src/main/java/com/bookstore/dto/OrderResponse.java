
package com.bookstore.dto;
import com.bookstore.entity.OrderStatus;
import com.bookstore.entity.PaymentStatus;
import lombok.*;
import java.time.Instant;
import java.util.List;
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private Double totalAmount;
    private Instant createdAt;
    private List<OrderItemDto> items;
    @Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class OrderItemDto {
        private String bookTitle;
        private Integer quantity;
        private Double priceSnapshot;
    }
}
