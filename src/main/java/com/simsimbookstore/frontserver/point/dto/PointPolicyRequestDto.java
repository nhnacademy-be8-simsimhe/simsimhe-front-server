package com.simsimbookstore.frontserver.point.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointPolicyRequestDto {

    private PointPolicy.EarningMethod earningMethod;
    private BigDecimal earningValue;
    private String description;
    private boolean available;
    private PointPolicy.EarningType earningType;
}