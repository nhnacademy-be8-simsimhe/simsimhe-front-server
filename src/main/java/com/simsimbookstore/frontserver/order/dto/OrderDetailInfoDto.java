package com.simsimbookstore.frontserver.order.dto;

import com.simsimbookstore.frontserver.delivery.policy.dto.Delivery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetailInfoDto {
    // order
    private String orderNumber;
    private LocalDateTime orderDate;
    private OrderHistoryResponseDto.OrderState orderState;
    private String senderName;
    private String senderPhoneNumber;
    private String senderEmail;
    private int pointEarn;
    private BigDecimal pointUse;
    private BigDecimal totalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal originalTotalPrice;

    // delivery
    private Long deliveryId;
    private String receiverName;
    private String receiverPhoneNumber;
    private Integer trackingNumber;
    private String postalCode;
    private String roadAddress;
    private String detailedAddress;
    private String reference;
    private Delivery.DeliveryState deliveryState;

    // payment_method
    private String paymentMethod;
}