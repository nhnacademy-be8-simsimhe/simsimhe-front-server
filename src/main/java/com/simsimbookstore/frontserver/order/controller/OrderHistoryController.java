package com.simsimbookstore.frontserver.order.controller;


import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import com.simsimbookstore.frontserver.order.service.OrderService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.util.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderService orderService;

    @GetMapping("/shop/users/orders")
    public String viewOrderHistory(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "15") int size,
                                   Model model) {


        if (customUserDetails == null) {
            return "redirect:/";
        }

        Long userId = customUserDetails.getUserId();

        PageResponse<OrderHistoryResponseDto> orderHistoryPage = orderService.getOrderHistory(userId, page, size);

        model.addAttribute("orderHistories", orderHistoryPage); // 이름 확인
        model.addAttribute("userId", userId);
        return "order/history/order_history"; // Thymeleaf 템플릿 이름
    }

}
