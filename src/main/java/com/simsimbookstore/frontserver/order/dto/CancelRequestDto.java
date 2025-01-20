package com.simsimbookstore.frontserver.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CancelRequestDto {
    private String cancelReason;
    private LocalDateTime refundedAt;

    public CancelRequestDto() {
        refundedAt = LocalDateTime.now();
    }
}
