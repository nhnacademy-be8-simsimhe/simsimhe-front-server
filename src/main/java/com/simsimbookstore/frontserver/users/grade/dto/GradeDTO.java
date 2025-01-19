package com.simsimbookstore.frontserver.users.grade.dto;

import java.math.BigDecimal;
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
public class GradeDTO {
    private String tier;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
}
