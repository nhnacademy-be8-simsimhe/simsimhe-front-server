package com.simsimbookstore.frontserver.order.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderRequestDto {
    @Setter
    private Long userId;
    @Setter
    private Long deliveryId;

    @NotNull(message = "할인전 가격은 필수입니다")
    @PositiveOrZero(message = "0 미만의 값은 들어올 수 없습니다")
    private BigDecimal originalPrice;

    @PositiveOrZero ( message = "음수 값은 들어올 수 없습니다")
    private BigDecimal pointUse;

    @Setter
    @PositiveOrZero ( message = "음수 값은 들어올 수 없습니다")
    private BigDecimal totalPrice;

    @Future(message = "과거의 날짜는 지정하실 수 없습니다")
    private LocalDate deliveryDate;

    @NotNull
    private String orderEmail;

    @NotNull
    private String phoneNumber;

    @PositiveOrZero ( message = "음수 값은 들어올 수 없습니다")
    private BigDecimal deliveryPrice;
    @NotNull
    private String senderName;
}