package com.simsimbookstore.frontserver.delivery.policy.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPolicy {
    private Long deliveryPolicyId;
    private String deliveryPolicyName;
    private BigDecimal deliveryPrice;
    private BigDecimal policyStandardPrice;
    private boolean standardPolicy;
}
