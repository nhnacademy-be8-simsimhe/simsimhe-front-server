package com.simsimbookstore.frontserver.order.controller;

import com.simsimbookstore.frontserver.delivery.policy.dto.Delivery;
import com.simsimbookstore.frontserver.order.dto.CancelRequestDto;
import com.simsimbookstore.frontserver.order.service.OrderService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderRefundController {
    private final OrderService orderService;

    // 환불 신청 페이지
    @GetMapping("/shop/users/orders/detail/{orderNumber}/refund")
    public String refund(@RequestParam List<String> orderName,
                         @RequestParam List<Integer> quantity,
                         @RequestParam Delivery.DeliveryState deliveryState,
                         @PathVariable String orderNumber,
                         Model model) {
        if (deliveryState.equals(Delivery.DeliveryState.PENDING)) {
            // 결제 취소
            model.addAttribute("orderName", orderName);
            model.addAttribute("quantity", quantity);
            return "order/refund/cancelApplyPage";
        } else {
            // 반품 신청
            model.addAttribute("orderName", orderName);
            model.addAttribute("quantity", quantity);
            return "order/refund/returnApplyPage";
        }
    }

    @PostMapping("/shop/users/orders/detail/{orderNumber}/refund")
    public ResponseEntity<Void> createRefund(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                             @PathVariable String orderNumber,
                                             @ModelAttribute CancelRequestDto cancelRequestDto) {
        Long userId = customUserDetails.getUserId();
        return orderService.applyRefund(orderNumber, cancelRequestDto, userId);

    }
}
