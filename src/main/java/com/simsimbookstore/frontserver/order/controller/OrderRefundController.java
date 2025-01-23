package com.simsimbookstore.frontserver.order.controller;

import com.simsimbookstore.frontserver.delivery.policy.dto.Delivery;
import com.simsimbookstore.frontserver.order.dto.CancelRequestDto;
import com.simsimbookstore.frontserver.order.dto.OrderBookState;
import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import com.simsimbookstore.frontserver.order.dto.ReturnRequestDto;
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

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class OrderRefundController {
    private final OrderService orderService;

    // 전체 환불 신청 페이지
    @GetMapping("/shop/users/orders/{orderNumber}/refund")
    public String refundTotal(@RequestParam String orderName,
                              @RequestParam int quantity,
                              @RequestParam OrderHistoryResponseDto.OrderState orderState,
                              @RequestParam List<OrderBookState> orderBookState,
                              @RequestParam Delivery.DeliveryState deliveryState,
                              @RequestParam Long orderBookId,
                              @PathVariable String orderNumber,
                              @RequestParam Long deliveryId,
                              Model model) {
        if ((orderState.equals(OrderHistoryResponseDto.OrderState.DELIVERY_READY))
                && (deliveryState.equals(Delivery.DeliveryState.READY))) {
            boolean isReady = orderBookState.stream()
                    .allMatch(state -> state.equals(OrderBookState.DELIVERY_READY));
            if (isReady) {
                // 결제 취소
                model.addAttribute("orderNumber", orderNumber);
                model.addAttribute("orderName", orderName);
                model.addAttribute("quantity", quantity);
                model.addAttribute("orderBookId", orderBookId);
                return "order/refund/totalCanceledApply";
            }
        }
        // 반품 신청
        model.addAttribute("orderName", orderName);
        model.addAttribute("quantity", quantity);
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("orderBookId", orderBookId);
        model.addAttribute("deliveryId", deliveryId);
        return "order/refund/totalReturnApplyPage";
    }

    // 전체 결제 취소 저장
    @PostMapping("/shop/users/orders/{orderNumber}/cancel")
    public String createCanceled(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 @PathVariable String orderNumber,
                                 @RequestParam String canceledReason,
                                 @RequestParam Long orderBookId) {
        Long userId = customUserDetails.getUserId();

        CancelRequestDto cancelRequestDto = new CancelRequestDto();
        cancelRequestDto.setCancelReason(canceledReason);
        cancelRequestDto.setOrderBookId(orderBookId);

        orderService.applyCanceled(orderNumber, userId, cancelRequestDto);
        return "order/refund/refundApplySuccess";
    }

    // 전체 주문 취소 저장
    @PostMapping("/shop/users/orders/{orderNumber}/refund")
    public String createRefund(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               @PathVariable String orderNumber,
                               @RequestParam String canceledReason,
                               @RequestParam Integer quantity,
                               @RequestParam(required = false) boolean damaged,
                               @RequestParam Long orderBookId,
                               @RequestParam Long deliveryId) {
        Long userId = customUserDetails.getUserId();

        if (Objects.isNull(damaged)) {
            damaged = false;
        }
        ReturnRequestDto returnRequestDto = new ReturnRequestDto(orderBookId, canceledReason, quantity, damaged, deliveryId);

        orderService.applyRefund(userId, orderNumber, returnRequestDto);
        return "order/refund/refundApplySuccess";
    }
}