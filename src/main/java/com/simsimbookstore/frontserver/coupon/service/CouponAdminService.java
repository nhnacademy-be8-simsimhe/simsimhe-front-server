package com.simsimbookstore.frontserver.coupon.service;

import com.simsimbookstore.frontserver.config.RabbitMqConfig;
import com.simsimbookstore.frontserver.coupon.client.CouponAdminClient;
import com.simsimbookstore.frontserver.coupon.dto.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponAdminService {
    private final CouponAdminClient couponAdminClient;
    private final RabbitTemplate rabbitTemplate;

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
    // RabbitMq 사용해서 쿠폰 발급
    public void issueCoupons(IssueCouponsRequestDto requestDto) {
//        couponAdminClient.issueCoupons(requestDto);
        List<Long> userIds = requestDto.getUserIds();
        for (Long userId : userIds) {
            IssueCouponsRequestDto rabbitMqRequestDto = IssueCouponsRequestDto.builder()
                    .userIds(List.of(userId))
                    .couponTypeId(requestDto.getCouponTypeId())
                    .build();
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.COUPON_ISSUE_QUEUE_ROUTING_KEY, rabbitMqRequestDto);
        }
    }

    public PageResponseDto<CouponResponseDto> getCoupons(Long userId, int page, int size, String sortField) {
        return couponAdminClient.getCoupons(userId, null, page, size);
    }

    public PageResponseDto<CouponResponseDto> getTotalCoupons(int page, int size) {
        return couponAdminClient.getTotalCoupons(page, size);
    }

    public List<CouponResponseDto> getExpiredCoupons() {
        return couponAdminClient.getExpiredCoupons();
    }

    public List<CouponResponseDto> getUnusedButDeadlinePassedCoupon() {
        return couponAdminClient.getUnusedButDeadlinePassedCoupon();
    }

    public void deleteCoupon(Long userId, Long couponId) {
        couponAdminClient.deleteCoupon(userId, couponId);
    }

    public CouponResponseDto expiredCoupon(Long userId, Long couponId) {
        return couponAdminClient.expiredCoupon(userId, couponId);
    }
}
