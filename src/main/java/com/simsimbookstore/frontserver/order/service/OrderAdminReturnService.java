package com.simsimbookstore.frontserver.order.service;

import com.simsimbookstore.frontserver.order.client.OrderAdminReturnClient;
import com.simsimbookstore.frontserver.order.dto.RefundResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderAdminReturnService {
    private final OrderAdminReturnClient orderAdminReturnClient;

    public Page<RefundResponseDto> refundList(Pageable pageable) {
        return orderAdminReturnClient.refundList(pageable);
    }

    public void confirmReturn(Long orderBookId) {
        orderAdminReturnClient.confirmReturn(orderBookId);
    }

    public void rejectReturn(Long orderBookId) {
        orderAdminReturnClient.rejectReturn(orderBookId);
    }
}