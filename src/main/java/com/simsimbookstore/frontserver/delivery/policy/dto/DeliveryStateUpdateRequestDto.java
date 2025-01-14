package com.simsimbookstore.frontserver.delivery.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStateUpdateRequestDto {
    private Delivery.DeliveryState newState; // 새로운 상태
}