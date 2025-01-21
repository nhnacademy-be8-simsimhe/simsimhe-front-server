package com.simsimbookstore.frontserver.order.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAdminResponseDto {
    private Long orderId;
    private String orderNumber;
    private BigDecimal totalPrice;
    private OrderHistoryResponseDto.OrderState orderState;
}