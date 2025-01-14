package com.simsimbookstore.frontserver.payment.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ConfirmResponseDto {
        private String orderId;
        private String paymentKey;
        private BigDecimal totalAmount;
        private String tossMethod;
        private String approvedAt;
        private String code;
        private String message;
}
