package com.simsimbookstore.frontserver.coupon.client;

import com.simsimbookstore.frontserver.coupon.dto.CouponResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "coupon-api-server", url = "http://localhost:8000/api/shop")
public interface CouponClient {
    @GetMapping("/coupons/{couponId}")
    public CouponResponseDto getCoupon(@PathVariable Long couponId);
}
