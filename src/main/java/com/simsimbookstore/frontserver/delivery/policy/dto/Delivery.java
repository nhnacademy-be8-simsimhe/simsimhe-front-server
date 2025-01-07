package com.simsimbookstore.frontserver.delivery.policy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    private Long deliveryId;

    private DeliveryState deliveryState;

    private String deliveryReceiver;

    private String receiverPhoneNumber;

    private Integer trackingNumber;

    private String postalCode;

    private String roadAddress;


    private String detailedAddress;

    private String reference;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum DeliveryState {
        PENDING,        // 배송대기
        READY,          // 배송준비
        IN_PROGRESS,    // 배송중
        COMPLETED,      // 배송완료
        RETURNED,       // 반품
        ERROR           // 배송오류
    }

    @JsonCreator
    public static DeliveryState fromValue(String value) {
        try {
            return DeliveryState.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid delivery state: " + value);
        }
    }

    public void updateDeliveryState(DeliveryState newDeliveryState) {
        this.deliveryState = newDeliveryState;
    }
}
