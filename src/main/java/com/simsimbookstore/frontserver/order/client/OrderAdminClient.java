package com.simsimbookstore.frontserver.order.client;

import com.simsimbookstore.frontserver.config.AuthenticationFeignConfig;
import com.simsimbookstore.frontserver.order.dto.OrderAdminResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderBookResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderBookStateUpdateRequestDto;
import com.simsimbookstore.frontserver.order.dto.OrderOrderBookResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderStateUpdateDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderBook-admin-api-server", url = "http://localhost:8000/api/admin/orders", configuration = AuthenticationFeignConfig.class)
public interface OrderAdminClient {

    @GetMapping()
    PageResponse<OrderResponseDto> getOrderBooks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size);

    @GetMapping("/{id}")
    OrderOrderBookResponseDto getOrderOrderBook(@PathVariable Long id);

    @PostMapping("/orderBook/{orderBookId}/state")
    OrderBookResponseDto updateOrderBookState(
            @PathVariable("orderBookId") Long orderBookId,
            @RequestBody OrderBookStateUpdateRequestDto requestDto);

    @PostMapping("/order/{orderId}/state")
    OrderAdminResponseDto updateOrderState(@PathVariable("orderId") Long orderId, @RequestBody
    OrderStateUpdateDto dto);
}
