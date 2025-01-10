package com.simsimbookstore.frontserver.payment.service;

import com.simsimbookstore.frontserver.order.dto.OrderFacadeRequestDto;
import com.simsimbookstore.frontserver.payment.client.PaymentClient;
import com.simsimbookstore.frontserver.payment.dto.ConfirmResponseDto;
import com.simsimbookstore.frontserver.payment.dto.SuccessRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    private final PaymentClient paymentClient;

    public String payment(OrderFacadeRequestDto dto) {
        return paymentClient.paymentInitiate(dto);
    }

    public ResponseEntity<ConfirmResponseDto> confirm(String paymentKey, String orderId, BigDecimal amount) {
        return paymentClient.successRequestDto(paymentKey, orderId, amount);
    }
}
