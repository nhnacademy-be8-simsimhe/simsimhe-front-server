package com.simsimbookstore.frontserver.coupon.client;

import com.simsimbookstore.frontserver.coupon.dto.*;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coupon-admin-api-server", url = "http://localhost:8000/api/admin")
public interface CouponAdminClient {
    @PostMapping("/couponPolicies")
    public CouponPolicyResponseDto createCouponPolicy(@RequestBody CouponPolicyRequestDto requestDto);

    @GetMapping("/couponPolicies")
    public PageResponseDto<CouponPolicyResponseDto> getCouponPolicyList(@RequestParam("page") int page,
                                                                        @RequestParam("size") int size);

    @PostMapping("/couponTypes")
    public CouponTypeResponseDto createCouponType(@RequestBody CouponTypeRequestDto requestDto);

    @GetMapping("/couponTypes")
    public PageResponseDto<CouponTypeResponseDto> getAllCouponType(@RequestParam("page") int page,
                                                                   @RequestParam("size") int size);
}
