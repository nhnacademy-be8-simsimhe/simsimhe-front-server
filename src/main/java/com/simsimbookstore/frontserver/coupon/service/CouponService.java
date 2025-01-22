package com.simsimbookstore.frontserver.coupon.service;

import com.simsimbookstore.frontserver.coupon.client.CouponClient;
import com.simsimbookstore.frontserver.coupon.dto.CouponResponseDto;
import com.simsimbookstore.frontserver.coupon.dto.PageResponseDto;
import com.simsimbookstore.frontserver.coupon.dto.UserCouponDiscountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {
    private final CouponClient couponClient;

    public PageResponseDto<CouponResponseDto> getUnusedCoupon(Long userId, int page, int size) {
        return couponClient.getUnusedCoupons(userId, page, size);
    }

    public PageResponseDto<UserCouponDiscountResponseDto> getUserCouponDiscount(Long userId, int page, int size) {
        return couponClient.getUserCouponDiscount(userId, page, size);
    }
}
