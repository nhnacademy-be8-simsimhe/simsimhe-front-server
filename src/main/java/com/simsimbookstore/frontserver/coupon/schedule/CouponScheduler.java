package com.simsimbookstore.frontserver.coupon.schedule;

import com.simsimbookstore.frontserver.config.RabbitMqConfig;
import com.simsimbookstore.frontserver.coupon.dto.CouponResponseDto;
import com.simsimbookstore.frontserver.coupon.dto.CouponType;
import com.simsimbookstore.frontserver.coupon.dto.IssueCouponsRequestDto;
import com.simsimbookstore.frontserver.coupon.service.CouponAdminService;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;
    private final CouponAdminService couponAdminService;
// 멀티 쓰레드 처리해야함. 스케줄러는 한개의 쓰레드로 동작하는것을 기본으로 함
// 쿠폰 만료 처리도 해야함 -> 1시간에 한번씩 만료 처리, 하루에 한번씩 만료 쿠폰 삭제

    @Scheduled(cron = "0 0 0 1 * *") //초 분 시 일 월 요일 -> 매달 1일 0시 0분 0초 (*은 모든 조건을 의미)
    public void issueBirthDayCoupon() throws InterruptedException {
        String month = String.valueOf(LocalDate.now().getMonthValue());
        List<UserResponse> allUserByBirth = userService.getAllUserByBirth(month);
        for (UserResponse userResponse : allUserByBirth) {
            IssueCouponsRequestDto issueCouponsRequestDto = IssueCouponsRequestDto.builder()
                    .couponTypeId(CouponType.BIRTHDAY_COUPON_TYPE_ID)
                    .userIds(List.of(userResponse.getUserId()))
                    .build();
            sleep(500);
//            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.COUPON_ISSUE_QUEUE_ROUTING_KEY, issueCouponsRequestDto);
            couponAdminService.issueCoupons(issueCouponsRequestDto);
        }
    }
    @Scheduled(cron = "0 0 * * * *")
    public void setExpiredCoupon() {
        List<CouponResponseDto> coupons = couponAdminService.getUnusedButDeadlinePassedCoupon();
        Map<String, Long> requestBody = new HashMap<>();
        for (CouponResponseDto coupon : coupons) {
            requestBody.put("userId", coupon.getUserId());
            requestBody.put("couponId", coupon.getCouponId());
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,RabbitMqConfig.COUPON_EXPIRE_QUEUE_ROUTING_KEY,requestBody);
        }
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteCoupon() {
        List<CouponResponseDto> expiredCoupons = couponAdminService.getExpiredCoupons();
        Map<String, Long> requestBody = new HashMap<>();
        for (CouponResponseDto coupon : expiredCoupons) {
            requestBody.put("userId", coupon.getUserId());
            requestBody.put("couponId", coupon.getCouponId());
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.COUPON_DELETE_QUEUE_ROUTING_KEY, requestBody);
        }
    }


}
