package com.simsimbookstore.frontserver.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
