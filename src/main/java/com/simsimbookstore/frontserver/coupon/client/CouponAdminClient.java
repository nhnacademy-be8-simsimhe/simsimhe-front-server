package com.simsimbookstore.frontserver.coupon.client;

import com.simsimbookstore.frontserver.config.AuthenticationFeignConfig;
import com.simsimbookstore.frontserver.coupon.dto.*;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "coupon-admin-api-server", url = "http://localhost:8000/api/admin",configuration = AuthenticationFeignConfig.class)
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

    @PostMapping("/coupons/issue")
    public void issueCoupons(@RequestBody IssueCouponsRequestDto requestDto);

    @GetMapping("/users/{userId}/coupons")
    public PageResponseDto<CouponResponseDto> getCoupons(@PathVariable Long userId,
                                                         @RequestParam(required = false) String sortField,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("size") int size);

    @GetMapping("/coupons")
    public PageResponseDto<CouponResponseDto> getTotalCoupons(@RequestParam("page") int page,
                                                              @RequestParam("size") int size);

    @GetMapping("/coupons/expired")
    public List<CouponResponseDto> getExpiredCoupons();

    @GetMapping("/coupons/unused/deadline-pass")
    public List<CouponResponseDto> getUnusedButDeadlinePassedCoupon();

    @PostMapping("/users/{userId}/coupons/expired")
    public CouponResponseDto expiredCoupon(@PathVariable Long userId,
                                                           @RequestParam Long couponId);

    @DeleteMapping("/users/{userId}/coupons/{couponId}")
    public void deleteCoupon(@PathVariable Long userId,
                                             @PathVariable Long couponId);
}
