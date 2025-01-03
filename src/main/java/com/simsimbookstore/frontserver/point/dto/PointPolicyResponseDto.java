package com.simsimbookstore.frontserver.point.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointPolicyResponseDto {
    private Long pointPolicyId;
    private PointPolicy.EarningMethod earningMethod;
    private BigDecimal earningValue;
    private String description;
    private boolean available;
    private PointPolicy.EarningType earningType;
}