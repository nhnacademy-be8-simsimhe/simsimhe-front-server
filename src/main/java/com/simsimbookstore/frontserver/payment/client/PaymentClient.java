package com.simsimbookstore.frontserver.payment.client;

import com.simsimbookstore.frontserver.order.dto.OrderFacadeRequestDto;
import com.simsimbookstore.frontserver.order.dto.RetryOrderRequestDto;
import com.simsimbookstore.frontserver.payment.dto.ConfirmResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "payment-api-server", url = "http://localhost:8000/api/shop/payment")
public interface PaymentClient {

    @PostMapping
    String paymentInitiate(@RequestBody OrderFacadeRequestDto dto);

    @RequestMapping(method = RequestMethod.GET, value = "/success")
    ResponseEntity<ConfirmResponseDto> successRequestDto(@RequestParam String paymentKey,
                                                         @RequestParam String orderId,
                                                         @RequestParam BigDecimal amount);

    @PostMapping("/retry")
    String paymentRetry(@RequestBody RetryOrderRequestDto dto);
}
