package com.simsimbookstore.frontserver.payment.controller;

import com.simsimbookstore.frontserver.payment.dto.ConfirmResponseDto;
import com.simsimbookstore.frontserver.payment.dto.SuccessRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.simsimbookstore.frontserver.order.dto.OrderFacadeRequestDto;
import com.simsimbookstore.frontserver.payment.service.PaymentService;
import feign.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

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

    @GetMapping("/payments/success")
    public String successUrl(@RequestParam String paymentKey,
                             @RequestParam String orderId,
                             @RequestParam BigDecimal amount,
                             Model model) {
        ResponseEntity<ConfirmResponseDto> confirmYN = paymentService.confirm(paymentKey, orderId, amount);

        if (confirmYN.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orderId", confirmYN.getBody().getOrderId());
            return "payment/success";
        }

        model.addAttribute("code", confirmYN.getBody().getCode());
        model.addAttribute("message", confirmYN.getBody().getMessage());
        return "payment/fail";
    }

    @GetMapping("/payments/fail")
    public String failUrl(HttpServletRequest request, Model model) {
        // 1. PAY_PROCESS_CANCELED : 구매자에 의한 취소 + orderId X -> 아무 처리 X(장바구니로 이동)
        if (request.getParameter("code").equals("PAY_PROCESS_CANCELED")) {
            return "redirect:/index";
        }

        if ((request.getParameter("code").equals("PAY_PROCESS_ABORTED")) || (request.getParameter("code").equals("REJECT_CARD_COMPANY"))) {
            // 2. PAY_PROCESS_ABORTED : 오류 메시지 확인 필요 / 구매자에게 안내
            // 3. REJECT_CARD_COMPANY : 구매자의 카드 정보가 문제 -> 구매자에게 안내
            model.addAttribute("code", request.getParameter("code"));
            model.addAttribute("message", request.getParameter("message"));
        }
        return "payment/fail";
    }
}
