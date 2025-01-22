package com.simsimbookstore.frontserver.order.client;

import com.simsimbookstore.frontserver.order.dto.CancelRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-cancel-api-server", url = "http://localhost:8000/api/shop")
public interface OrderCanceledClient {

    @PostMapping("/payment/canceled/{orderNumber}")
    ResponseEntity<Void> applyCanceled(@PathVariable String orderNumber,
                                       @RequestParam Long userId,
                                       @RequestBody CancelRequestDto cancelRequestDto);
}
