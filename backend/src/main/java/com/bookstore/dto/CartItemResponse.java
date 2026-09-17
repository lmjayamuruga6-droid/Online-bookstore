
package com.bookstore.dto;
import lombok.*;
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class CartItemResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Double priceSnapshot;
    private Double lineTotal;
}
