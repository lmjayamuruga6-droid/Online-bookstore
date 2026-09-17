
package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String idempotencyKey; // FIX #14

    private Double totalAmount;
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    // FIX #10: Factory / Domain method
    public static Order createFrom(Cart cart, User user, String idempotencyKey) {
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.CREATED)
                .paymentStatus(PaymentStatus.PENDING)
                .idempotencyKey(idempotencyKey)
                .createdAt(Instant.now())
                .build();
        double total = 0;
        for (CartItem ci : cart.getItems()) {
            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .book(ci.getBook())
                    .quantity(ci.getQuantity())
                    .priceSnapshot(ci.getPriceSnapshot())
                    .build();
            order.getItems().add(oi);
            total += ci.getPriceSnapshot() * ci.getQuantity();
        }
        order.setTotalAmount(total);
        return order;
    }
}
