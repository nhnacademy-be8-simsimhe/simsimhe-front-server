package com.simsimbookstore.frontserver.order.client;

import com.simsimbookstore.frontserver.order.dto.CancelRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-refund-api-server", url = "http://localhost:8000/api/shop/users")
public interface OrderRefundClient {

    @GetMapping("/{userId}/orders/{orderNumber}")
    ResponseEntity<Void> refund(@PathVariable Long userId,
                             @PathVariable String orderNumber,
                             @RequestBody CancelRequestDto cancelRequestDto);
}
