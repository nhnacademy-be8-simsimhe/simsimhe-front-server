package com.simsimbookstore.frontserver.coupon.schedule;

import com.simsimbookstore.frontserver.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 1000)
    public void sender1() {
        String time = LocalDateTime.now().toString();
        System.out.println(time);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,RabbitMqConfig.COUPON_QUEUE_ROUTING_KEY,time);
    }
}
