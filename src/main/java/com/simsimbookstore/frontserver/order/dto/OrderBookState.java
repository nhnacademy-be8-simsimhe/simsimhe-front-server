package com.simsimbookstore.frontserver.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderBookState {
    PENDING,          //결제대기
    DELIVERY_READY,   // 배송대기
    IN_DELIVERY,      // 배송중
    COMPLETED,        // 완료
    RETURNED,         // 반품
    PAYMENT_CANCELED,          // 결제취소
    CANCELED,
    ORDER_CANCELED
}