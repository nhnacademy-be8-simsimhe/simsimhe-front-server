package com.simsimbookstore.frontserver.order.controller;

import com.simsimbookstore.frontserver.order.dto.RefundResponseDto;
import com.simsimbookstore.frontserver.order.service.OrderAdminReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class OrderAdminReturnController {
    private final OrderAdminReturnService orderAdminReturnService;

    // 환불 리스트
    @GetMapping("/admin/order/refund")
    public String refundList(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("returnDate").descending());
        Page<RefundResponseDto> refundInfoList = orderAdminReturnService.refundList(pageable);

        for (RefundResponseDto refundResponseDto : refundInfoList) {
            int quantity = refundResponseDto.getQuantity();
            BigDecimal salePrice = refundResponseDto.getSalePrice();
            BigDecimal price = salePrice.multiply(BigDecimal.valueOf(quantity));
            model.addAttribute("price", price);
        }

        model.addAttribute("refundPage", refundInfoList.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", refundInfoList.getTotalPages());
        model.addAttribute("totalItems", refundInfoList.getTotalElements());

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