package com.simsimbookstore.frontserver.order.controller;

import com.simsimbookstore.frontserver.order.dto.RefundResponseDto;
import com.simsimbookstore.frontserver.order.service.OrderAdminReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderAdminReturnController {
    private final OrderAdminReturnService orderAdminReturnService;

    // 환불 리스트
    @GetMapping("/admin/order/refund")
    public String refundList(Model model) {
        List<RefundResponseDto> refundInfoList = orderAdminReturnService.refundList();

        for (RefundResponseDto refundResponseDto : refundInfoList) {
            int quantity = refundResponseDto.getQuantity();
            BigDecimal salePrice = refundResponseDto.getSalePrice();
            BigDecimal price = salePrice.multiply(BigDecimal.valueOf(quantity));
            model.addAttribute("price", price);
        }

        model.addAttribute("refundList", refundInfoList);
        return "admin/order/refundConfirm";
    }

    // 환불 승인
    @PostMapping("/admin/return/confirm")
    public String confirmReturn(@RequestParam Long orderBookId) {
        orderAdminReturnService.confirmReturn(orderBookId);
        return "admin/order/confirmSuccess";
    }

    // 환불 반려
    @PostMapping("/admin/return/reject")
    public String confirmReject(@RequestParam Long orderBookId) {
        orderAdminReturnService.rejectReturn(orderBookId);
        return "admin/order/rejectSuccess";
    }
}
