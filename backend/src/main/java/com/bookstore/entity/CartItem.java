
package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "book_id"}))
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer quantity;

    // FIX #9: price snapshot at time of adding
    private Double priceSnapshot;
}
