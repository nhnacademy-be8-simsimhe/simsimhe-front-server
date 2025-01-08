package com.simsimbookstore.frontserver.point.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PointPolicy {
    private Long pointPolicyId;

    private EarningMethod earningMethod;

    private EarningType earningType;

    private BigDecimal earningValue;

    private Boolean isAvailable;

    private String description;

    public enum EarningType {
        FIX, RATE
    }

    public enum EarningMethod {
        SIGNUP, REVIEW, PHOTOREVIEW,
        ORDER_STANDARD, ORDER_ROYAL, ORDER_GOLD, ORDER_PLATINUM
    }
}