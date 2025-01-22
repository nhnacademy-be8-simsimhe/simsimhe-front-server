package com.simsimbookstore.frontserver.order.service;

import com.simsimbookstore.frontserver.order.client.OrderAdminReturnClient;
import com.simsimbookstore.frontserver.order.dto.RefundResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderAdminReturnService {
    private final OrderAdminReturnClient orderAdminReturnClient;

    public List<RefundResponseDto> refundList() {
        return orderAdminReturnClient.refundList();
    }

    public void confirmReturn(Long orderBookId) {
        orderAdminReturnClient.confirmReturn(orderBookId);
    }

    public void rejectReturn(Long orderBookId) {
        orderAdminReturnClient.rejectReturn(orderBookId);
    }
}
