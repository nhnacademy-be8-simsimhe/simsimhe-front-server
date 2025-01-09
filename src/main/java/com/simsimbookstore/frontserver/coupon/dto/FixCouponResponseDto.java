package com.simsimbookstore.frontserver.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FixCouponResponseDto extends CouponResponseDto{

    private final DisCountType disCountType = DisCountType.FIX;
    // 할인액
    private BigDecimal discountPrice;
    // 최소 주문 금액
    private BigDecimal minOrderAmount;

}
