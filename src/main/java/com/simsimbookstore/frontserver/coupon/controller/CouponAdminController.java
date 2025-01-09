package com.simsimbookstore.frontserver.coupon.controller;

import com.simsimbookstore.frontserver.coupon.dto.CouponPolicyRequestDto;
import com.simsimbookstore.frontserver.coupon.dto.CouponPolicyResponseDto;
import com.simsimbookstore.frontserver.coupon.dto.CouponTypeRequestDto;
import com.simsimbookstore.frontserver.coupon.service.CouponAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CouponAdminController {

    private final CouponAdminService couponAdminService;

    @GetMapping("/couponPolicies/create")
    public String createCouponPolicyForm() {
        return "admin/coupon/couponPolicy/addCouponPolicy";
    }

    @PostMapping("/couponPolicies/create")
    public String createCouponPolicy(@ModelAttribute @Valid CouponPolicyRequestDto requestDto,
                                     Model model) {
        CouponPolicyResponseDto responseDto = couponAdminService.createCouponPolicy(requestDto);
        model.addAttribute("couponPolicy", requestDto);

        return "admin/coupon/couponPolicy/successCreateCouponPolicy";

    }
}
