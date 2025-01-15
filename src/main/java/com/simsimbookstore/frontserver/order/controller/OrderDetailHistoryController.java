package com.simsimbookstore.frontserver.order.controller;

import com.simsimbookstore.frontserver.order.dto.*;
import com.simsimbookstore.frontserver.order.service.OrderService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderDetailHistoryController {
    private final OrderService orderService;

    @GetMapping("/shop/users/orders/detail/{orderNumber}")
    public String orderDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                              @PathVariable String orderNumber,
                              Model model) {
        Long userId = customUserDetails.getUserId();

        OrderDetailResponseDto orderDetailDto = orderService.orderDetailHistory(userId, orderNumber);

        // 주문 정보
        model.addAttribute("orderDetail", orderDetailDto.getOrderDetailInfoDto());

        // 주문 상품 리스트
        model.addAttribute("orderProductInfo", orderDetailDto.getOrderDetailProductList());

        // 주문 금액 및 결제 정보
        // 총 쿠폰 할인 금액
        List<OrderDetailProduct> product = orderDetailDto.getOrderDetailProductList();
        for (OrderDetailProduct couponPrice : product) {
            BigDecimal totalPrice;
            BigDecimal coupon = couponPrice.getCouponPrice();
            if (product.size() > 1) {
                totalPrice = coupon.add(coupon);
            } else {
                totalPrice = coupon;
            }
            model.addAttribute("totalCouponPrice", totalPrice);
        }

        // 총 포장 비용 금액
        for (OrderDetailProduct wrapPrice : product) {
            BigDecimal totalWrapPrice;
            BigDecimal wrap = wrapPrice.getPackagePrice();
            if (product.size() > 1) {
                totalWrapPrice = wrap.add(wrap);
            } else {
                totalWrapPrice = wrap;
            }
            model.addAttribute("totalWrapPrice", totalWrapPrice);
        }

        return "order/history/detail/order_history_detail";
    }
}
