package com.simsimbookstore.frontserver.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetailResponseDto {
    OrderDetailInfoDto orderDetailInfoDto;
    List<OrderDetailProduct> orderDetailProductList;
}
