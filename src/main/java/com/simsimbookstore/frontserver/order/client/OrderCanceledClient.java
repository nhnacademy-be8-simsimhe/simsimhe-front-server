package com.simsimbookstore.frontserver.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-cancel-api-server", url = "http://localhost:8000/api/shop")
public interface OrderCanceledClient {

    @PostMapping("/payment/canceled/{orderNumber}")
    ResponseEntity<Void> applyCanceled(@PathVariable String orderNumber,
                                 @RequestParam Long userId,
                                 @RequestParam String canceledReason);
}
