
package com.bookstore.dto;
import lombok.*;
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
}
