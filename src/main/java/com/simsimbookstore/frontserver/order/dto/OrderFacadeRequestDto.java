package com.simsimbookstore.frontserver.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFacadeRequestDto {
    DeliveryRequestDto deliveryRequestDto;
    MemberOrderRequestDto memberOrderRequestDto;
    List<OrderBookRequestDto> orderBookRequestDtos;
    String method;
}