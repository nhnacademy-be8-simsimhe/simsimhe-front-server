package com.simsimbookstore.frontserver.delivery.policy.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPolicyRequestDto {

    @NotBlank(message = "Delivery policy name is required.")
    @Size(max = 100, message = "Delivery policy name must be less than 100 characters.")
    private String deliveryPolicyName;

    @NotNull(message = "Delivery policy standard price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Policy standard price must be greater than 0.")
    private BigDecimal policyStandardPrice;

    private boolean standardPolicy;

    @NotNull(message = "Delivery price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Delivery price must be greater than 0.")
    private BigDecimal deliveryPrice;

}