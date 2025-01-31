package com.simsimbookstore.frontserver.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDto {

    @NotNull(message = "DeliveryState는 필수입니다.")
    private DeliveryState deliveryState;

    @NotBlank(message = "수령인 이름은 필수입니다.")
    @Size(max = 20, message = "수령인 이름은 최대 20자까지 가능합니다.")
    private String deliveryReceiver;

    @NotBlank(message = "수령인 전화번호는 필수입니다.")
    @Size(max = 20, message = "수령인 전화번호는 최대 20자까지 가능합니다.")
    private String receiverPhoneNumber;

    @Size(max = 10, message = "운송장 번호는 최대 20자리까지 가능합니다.")
    private Integer trackingNumber;

    @NotBlank(message = "우편번호는 필수입니다.")
    @Size(max = 5, message = "우편번호는 최대 5자리까지 가능합니다.")
    private String postalCode;

    @NotBlank(message = "도로명 주소는 필수입니다.")
    @Size(max = 255, message = "도로명 주소는 최대 255자까지 가능합니다.")
    private String roadAddress;

    @NotBlank(message = "상세 주소는 필수입니다.")
    @Size(max = 255, message = "상세 주소는 최대 255자까지 가능합니다.")
    private String detailedAddress;

    @Size(max = 30, message = "참조 정보는 최대 30자까지 가능합니다.")
    private String reference;


    public enum DeliveryState {
        PENDING,        // 배송대기
        READY,          // 배송준비
        IN_PROGRESS,    // 배송중
        COMPLETED,      // 배송완료
        RETURNED,       // 반품
        ERROR           // 배송오류
    }
}