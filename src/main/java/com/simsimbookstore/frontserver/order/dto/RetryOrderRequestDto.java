package com.simsimbookstore.frontserver.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetryOrderRequestDto {
    Long userId;
    String orderNumber;
    String method;
}
