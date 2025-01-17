package com.simsimbookstore.frontserver.order.client;

import com.simsimbookstore.frontserver.order.dto.OrderDetailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-detail-api-server", url = "http://localhost:8000/api/shop/users")
public interface OrderDetailClient {
    @GetMapping("/{userId}/orders/{orderNumber}")
    ResponseEntity<OrderDetailResponseDto> getOrderDetailHistory(@PathVariable Long userId, @PathVariable String orderNumber);

    @GetMapping("/guest-orders")
    OrderDetailResponseDto guestOrderDetail(@RequestParam String orderNumber,
                                            @RequestParam String email);


}