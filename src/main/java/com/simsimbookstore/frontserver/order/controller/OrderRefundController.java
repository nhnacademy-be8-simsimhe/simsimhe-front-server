package com.simsimbookstore.frontserver.order.controller;

import com.simsimbookstore.frontserver.delivery.policy.dto.Delivery;
import com.simsimbookstore.frontserver.order.dto.OrderBookState;
import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import com.simsimbookstore.frontserver.order.service.OrderService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderRefundController {
    private final OrderService orderService;

//    // 부분 환불 신청 페이지
//    @GetMapping("/shop/users/orders/detail/{orderNumber}/refund")
//    public String refund(@RequestParam List<String> orderName,
//                         @RequestParam List<Integer> quantity,
//                         @RequestParam Delivery.DeliveryState deliveryState,
//                         @PathVariable String orderNumber,
//                         Model model) {
//        if (deliveryState.equals(Delivery.DeliveryState.PENDING)) {
//            // 결제 취소
//            model.addAttribute("orderName", orderName);
//            model.addAttribute("quantity", quantity);
//            return "order/refund/totalCanceledApply";
//        } else {
//            // 반품 신청
//            model.addAttribute("orderName", orderName);
//            model.addAttribute("quantity", quantity);
//            return "order/refund/returnApplyPage";
//        }
//    }

    // 전체 환불 신청 페이지
    @GetMapping("/shop/users/orders/detail/{orderNumber}/refund")
    public String refund(@RequestParam String orderName,
                         @RequestParam int quantity,
                         @RequestParam OrderHistoryResponseDto.OrderState orderState,
                         @RequestParam List<OrderBookState> orderBookState,
                         @RequestParam Delivery.DeliveryState deliveryState,
                         @PathVariable String orderNumber,
                         Model model) {
        if ((orderState.equals(OrderHistoryResponseDto.OrderState.DELIVERY_READY))
        && (deliveryState.equals(Delivery.DeliveryState.READY))) {
            boolean isReady = orderBookState.stream()
                    .allMatch(state -> state.equals(OrderBookState.DELIVERY_READY));
            if (isReady) {
                // 결제 취소
                model.addAttribute("orderName", orderName);
                model.addAttribute("quantity", quantity);
                return "order/refund/totalCanceledApply";
            }
        }
        // 반품 신청
        model.addAttribute("orderName", orderName);
        model.addAttribute("quantity", quantity);
        return "order/refund/returnApplyPage";
    }

    @PostMapping("/shop/users/orders/detail/{orderNumber}/refund")
    public String createRefund(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               @PathVariable String orderNumber,
                               @RequestParam String canceledReason) {
        Long userId = customUserDetails.getUserId();
        String reason = URLEncoder.encode(canceledReason, StandardCharsets.UTF_8);
        orderService.applyCanceled(orderNumber, userId, canceledReason);
        return "order/refund/canceledApplySuccess";
    }
}
