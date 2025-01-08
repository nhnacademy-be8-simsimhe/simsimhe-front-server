package com.simsimbookstore.frontserver.payment.service;

import com.simsimbookstore.frontserver.order.dto.OrderFacadeRequestDto;
import com.simsimbookstore.frontserver.payment.client.PaymentClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    private final PaymentClient paymentClient;

    public String payment(OrderFacadeRequestDto dto) {
        return paymentClient.paymentInitiate(dto);
    }
}
