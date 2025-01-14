package com.simsimbookstore.frontserver.coupon.service;

import com.simsimbookstore.frontserver.coupon.client.CouponAdminClient;
import com.simsimbookstore.frontserver.coupon.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponAdminService {
    private final CouponAdminClient couponAdminClient;

    public CouponPolicyResponseDto createCouponPolicy(CouponPolicyRequestDto requestDto) {
        return couponAdminClient.createCouponPolicy(requestDto);
    }

    public PageResponseDto<CouponPolicyResponseDto> getCouponPolicyList(int page, int size ) {
        return couponAdminClient.getCouponPolicyList(page, size);
    }

    public CouponTypeResponseDto createCouponType(CouponTypeRequestDto requestDto) {
        return couponAdminClient.createCouponType(requestDto);
    }

    public PageResponseDto<CouponTypeResponseDto> getAllCouponType(int page, int size) {
        return couponAdminClient.getAllCouponType(page, size);
    }

    public Map<String, List<Long>> issueCoupons(IssueCouponsRequestDto requestDto) {
        return couponAdminClient.issueCoupons(requestDto);
    }

    public PageResponseDto<CouponResponseDto> getCoupons(Long userId, int page, int size, String sortField) {
        return couponAdminClient.getCoupons(userId, null, page, size);
    }

    public PageResponseDto<CouponResponseDto> getTotalCoupons(int page, int size) {
        return couponAdminClient.getTotalCoupons(page, size);
    }
}
