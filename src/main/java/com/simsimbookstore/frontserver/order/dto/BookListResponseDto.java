package com.simsimbookstore.frontserver.order.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookListResponseDto {
    Long bookId;
    String title;
    BigDecimal price;
    Integer quantity;
}
