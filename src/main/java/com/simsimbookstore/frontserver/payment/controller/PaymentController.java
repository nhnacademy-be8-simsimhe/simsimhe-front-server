package com.simsimbookstore.frontserver.payment.controller;

import com.simsimbookstore.frontserver.order.dto.OrderFacadeRequestDto;
import com.simsimbookstore.frontserver.payment.service.PaymentService;
import feign.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping
    @RequestMapping("/shop/payment")
    @ResponseBody
    public ResponseEntity<?> paymentInitiate(@RequestBody OrderFacadeRequestDto dto) {
        return ResponseEntity.ok(paymentService.payment(dto));
    }
}
