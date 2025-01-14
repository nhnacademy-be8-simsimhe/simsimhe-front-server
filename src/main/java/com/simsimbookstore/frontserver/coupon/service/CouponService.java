package com.simsimbookstore.frontserver.coupon.service;

import com.simsimbookstore.frontserver.coupon.client.CouponClient;
import com.simsimbookstore.frontserver.coupon.dto.CouponResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {
    private final CouponClient couponClient;

    public CouponResponseDto getCoupon(Long couponId) {
        CouponResponseDto coupon = couponClient.getCoupon(couponId);
        return coupon;
    }
}
