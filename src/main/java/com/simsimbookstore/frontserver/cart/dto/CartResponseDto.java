package com.simsimbookstore.frontserver.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartResponseDto {
    private Long bookId;
    private String userId;
    private int quantity;
    private String title;
    private String imagePath;
    private int price;
    private int bookQuantity;
}
