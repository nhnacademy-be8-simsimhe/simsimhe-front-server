package com.simsimbookstore.frontserver.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartRequestDto {

    private int quantity; //담을 도서 수량

    private String bookId;

    private String userId;
}
