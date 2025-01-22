package com.simsimbookstore.frontserver.coupon.client;

import com.simsimbookstore.frontserver.coupon.dto.CouponResponseDto;
import com.simsimbookstore.frontserver.coupon.dto.PageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coupon-api-server", url = "http://localhost:8000/api/shop")
public interface CouponClient {
    @GetMapping(value = "/users/{userId}/coupons/unused")
    public PageResponseDto<CouponResponseDto> getUnusedCoupons(@PathVariable Long userId,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("size") int size);
}
