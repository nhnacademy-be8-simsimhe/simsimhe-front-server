package com.simsimbookstore.frontserver.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RateCouponResponseDto extends CouponResponseDto{

    // 할인 형태
    private final DisCountType disCountType = DisCountType.RATE;
    // 할인율
    private BigDecimal discountRate;
    // 최대 할인 금액
    private BigDecimal maxDiscountAmount;
    // 최소 주문 금액
    private BigDecimal minOrderAmount;
}
