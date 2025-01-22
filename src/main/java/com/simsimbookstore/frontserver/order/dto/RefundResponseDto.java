package com.simsimbookstore.frontserver.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundResponseDto {
    private Long returnId;         // 반품 ID
    private String returnReason;   // 반품 사유
    private LocalDateTime returnDate; // 반품 날짜
    private String returnState;    // 반품 상태
    private Integer quantity;      // 수량
    private Boolean refund;        // 환불 여부
    private Boolean damaged;       // 손상 여부

    private Long bookId;           // 책 ID
    private String bookTitle;      // 책 제목

    private String orderName;
    private BigDecimal salePrice;
    private LocalDateTime confirmDate;  // 승인 날짜
    private Long orderBookId;
}
