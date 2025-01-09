package com.simsimbookstore.frontserver.coupon.service;

import com.simsimbookstore.frontserver.coupon.client.CouponAdminClient;
import com.simsimbookstore.frontserver.coupon.dto.CouponPolicyRequestDto;
import com.simsimbookstore.frontserver.coupon.dto.CouponPolicyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponAdminService {
    private final CouponAdminClient couponAdminClient;

    public CouponPolicyResponseDto createCouponPolicy(CouponPolicyRequestDto requestDto) {
        return couponAdminClient.createCouponPolicy(requestDto);
    }
}
