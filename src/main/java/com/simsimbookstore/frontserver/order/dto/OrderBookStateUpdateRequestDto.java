package com.simsimbookstore.frontserver.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBookStateUpdateRequestDto {
    private OrderBookState newOrderBookState;


    public enum OrderBookState {
        PENDING,          //결제대기
        DELIVERY_READY,   // 배송대기
        IN_DELIVERY,      // 배송중
        COMPLETED,        // 완료
        RETURNED,         // 반품
        CANCELED          // 결제취소
    }
}