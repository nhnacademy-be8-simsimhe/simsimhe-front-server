package com.simsimbookstore.frontserver.order.controller;


import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import com.simsimbookstore.frontserver.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderService orderService;

    @GetMapping("/shop/users/orders")
    public String viewOrderHistory(@AuthenticationPrincipal Long userId,
                                   @PageableDefault(size = 10) Pageable pageable,
                                   Model model) {

        if (userId == null) {
            return "redirect:/";
        }
        Page<OrderHistoryResponseDto> orderHistoryPage = orderService.getOrderHistory(userId, pageable);

        model.addAttribute("orderHistories", orderHistoryPage.getContent());
        model.addAttribute("page", orderHistoryPage);
        model.addAttribute("userId", userId);


        return "order/history/order_history"; // order-history.html 파일을 렌더링
    }
}
