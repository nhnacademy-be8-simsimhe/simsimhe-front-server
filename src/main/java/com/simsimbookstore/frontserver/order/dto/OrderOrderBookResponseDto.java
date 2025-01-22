package com.simsimbookstore.frontserver.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOrderBookResponseDto {

    OrderAdminResponseDto orderResponseDto;
    List<OrderBookAdminResponseDto> orderBookAdminResponseDtos;
}