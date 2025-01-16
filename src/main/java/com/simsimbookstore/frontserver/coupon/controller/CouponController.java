package com.simsimbookstore.frontserver.coupon.controller;

import com.simsimbookstore.frontserver.coupon.dto.CouponResponseDto;
import com.simsimbookstore.frontserver.coupon.dto.PageResponseDto;
import com.simsimbookstore.frontserver.coupon.service.CouponService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/user/coupons")
    public String getUserUnUsedCoupons(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Model model) {

        Long userId = customUserDetails.getUserId();
        PageResponseDto<CouponResponseDto> unusedCoupon = couponService.getUnusedCoupon(userId, page, size);
        model.addAttribute("unusedCoupon", unusedCoupon);
        return "users/myPage/coupons";
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
