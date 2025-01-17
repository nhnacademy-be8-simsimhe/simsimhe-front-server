package com.simsimbookstore.frontserver.order.controller;

import com.simsimbookstore.frontserver.order.dto.*;
import com.simsimbookstore.frontserver.order.service.OrderService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
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

        return "order/history/detail/orderHistoryDetail";
    }


    @GetMapping("/shop/guest-order/search")
    public String showGuestOrderSearchForm() {
        // "guest_order_search.html"로 이동
        return "order/history/detail/guest_order_search";
    }

    @GetMapping("/shop/guest/orders/detail")
    public String guestOrderDetail(@RequestParam String orderNumber,
                                   @RequestParam String email,
                                   Model model) {
        // 게스트 주문 상세 조회 호출
        OrderDetailResponseDto orderDetailDto = orderService.guestOrderDetail(orderNumber, email);

        // 주문 정보
        model.addAttribute("orderDetail", orderDetailDto.getOrderDetailInfoDto());

        // 주문 상품 리스트
        model.addAttribute("orderProductInfo", orderDetailDto.getOrderDetailProductList());

        // 주문 금액 및 결제 정보
        // 총 쿠폰 할인 금액 계산
        List<OrderDetailProduct> products = orderDetailDto.getOrderDetailProductList();
        BigDecimal totalCouponPrice = products.stream()
                .map(OrderDetailProduct::getCouponPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalCouponPrice", totalCouponPrice);

        // 총 포장 비용 계산
        BigDecimal totalWrapPrice = products.stream()
                .map(OrderDetailProduct::getPackagePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalWrapPrice", totalWrapPrice);

        return "order/history/detail/order_history_detail";
    }
}
