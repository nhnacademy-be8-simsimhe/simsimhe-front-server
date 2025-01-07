package com.simsimbookstore.frontserver.payment.client;


import com.simsimbookstore.frontserver.order.dto.OrderFacadeRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-api-server", url = "http://localhost:8000/api/shop/payment")
public interface PaymentClient {
    @PostMapping
    String paymentInitiate(@RequestBody OrderFacadeRequestDto dto);
}
