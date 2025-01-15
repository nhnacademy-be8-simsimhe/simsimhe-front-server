package com.simsimbookstore.frontserver.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponseDto {
    private String orderNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;
    private String orderName;
    private BigDecimal orderAmount;
    private OrderState orderState;
    private String trackingNumber;
    private String orderUserName;
    private String receiverName;

    public enum OrderState {
        PENDING,           // 주문대기
        DELIVERY_READY,
        IN_DELIVERY,       // 배송중
        COMPLETED,         // 완료
        RETURNED,          // 반품
        ORDER_CANCELED,    // 주문취소
        PAYMENT_CANCELED   // 결제취소
    }
}