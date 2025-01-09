package com.simsimbookstore.frontserver.coupon.client;

import com.simsimbookstore.frontserver.coupon.dto.CouponPolicyRequestDto;
import com.simsimbookstore.frontserver.coupon.dto.CouponPolicyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "coupon-admin-api-server",url = "http://localhost:8000/api/admin")
public interface CouponAdminClient {
    @PostMapping("/couponPolicies")
    public CouponPolicyResponseDto createCouponPolicy(CouponPolicyRequestDto requestDto);
}
