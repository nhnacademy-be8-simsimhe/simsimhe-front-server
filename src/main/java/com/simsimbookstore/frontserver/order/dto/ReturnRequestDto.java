package com.simsimbookstore.frontserver.order.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReturnRequestDto {
    private Long orderBookId;
    private String returnReason;
    private LocalDateTime returnDate;
    private Integer quantity;
    private boolean isDamaged;
    private ReturnState returnState;
    private boolean refund;
    private Long deliveryId;

    public ReturnRequestDto(Long orderBookId, String returnReason, Integer quantity, boolean isDamaged, Long deliveryId) {
        this.orderBookId = orderBookId;
        this.returnReason = returnReason;
        this.quantity = quantity;
        this.isDamaged = isDamaged;
        this.deliveryId = deliveryId;

        this.returnDate = LocalDateTime.now();
        this.returnState = ReturnState.RETURN_REQUESTED;
        this.refund = false;
    }
}
