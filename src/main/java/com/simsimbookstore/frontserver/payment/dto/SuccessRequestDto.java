package com.simsimbookstore.frontserver.payment.dto;

import java.math.BigDecimal;

public class SuccessRequestDto {
    private String paymentKey;
    private String orderId;
    private BigDecimal amount;
}
