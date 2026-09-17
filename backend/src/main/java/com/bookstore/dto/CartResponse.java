
package com.bookstore.dto;
import lombok.*;
import java.util.List;
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class CartResponse {
    private Long cartId;
    private List<CartItemResponse> items;
    private Double total;
}
