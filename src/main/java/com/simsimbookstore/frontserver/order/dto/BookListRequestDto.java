package com.simsimbookstore.frontserver.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookListRequestDto {

    Long bookId;
    Integer quantity;

}