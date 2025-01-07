package com.simsimbookstore.frontserver.payment.client;

import com.simsimbookstore.frontserver.order.dto.OrderFacadeRequestDto;
import com.simsimbookstore.frontserver.payment.dto.SuccessRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@FeignClient(name = "payment-api-server", url = "http://localhost:8000/api/shop/payment")
public interface PaymentClient {

    //@GetMapping("/success")
    @RequestMapping(method = RequestMethod.GET, value = "/success")
    SuccessRequestDto successRequestDto(@RequestParam String paymentKey,
                                        @RequestParam String orderId,
                                        @RequestParam BigDecimal amount);

    @PostMapping
    String paymentInitiate(@RequestBody OrderFacadeRequestDto dto);
}
