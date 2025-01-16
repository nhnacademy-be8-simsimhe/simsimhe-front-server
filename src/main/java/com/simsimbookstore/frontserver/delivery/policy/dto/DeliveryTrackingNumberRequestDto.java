package com.simsimbookstore.frontserver.delivery.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTrackingNumberRequestDto {
    private Integer trackingNumber;
}