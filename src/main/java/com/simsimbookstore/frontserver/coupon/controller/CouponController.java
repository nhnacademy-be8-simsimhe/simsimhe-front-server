package com.simsimbookstore.frontserver.coupon.controller;

import com.simsimbookstore.frontserver.coupon.dto.CouponResponseDto;
import com.simsimbookstore.frontserver.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/coupons/{couponId}")
    public String getCoupon(@PathVariable Long couponId,
                            Model model) {
        CouponResponseDto coupon = couponService.getCoupon(couponId);
        System.out.println(coupon);
        return "index";

    }
    private Pageable setPageable(Pageable pageable, String sortField) {
        String sortBy = (StringUtils.isEmpty(sortField) ? "issueDate" : sortField);
        return PageRequest.of(
                pageable.getPageNumber(),
                10, //페이지 사이즈를 10으로 고정
                Sort.by(Sort.Direction.ASC, sortBy)
        );
    }
}
