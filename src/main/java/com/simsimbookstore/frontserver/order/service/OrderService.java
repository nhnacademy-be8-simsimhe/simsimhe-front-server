package com.simsimbookstore.frontserver.order.service;

import com.simsimbookstore.frontserver.order.client.OrderCanceledClient;
import com.simsimbookstore.frontserver.order.client.OrderClient;
import com.simsimbookstore.frontserver.order.client.OrderDetailClient;
import com.simsimbookstore.frontserver.order.client.OrderHistoryClient;
import com.simsimbookstore.frontserver.order.dto.*;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderClient orderClient;
    private final OrderHistoryClient orderHistoryClient;
    private final OrderDetailClient orderDetailClient;
    private final OrderCanceledClient orderCanceledClient;

    @Transactional
    public List<BookListResponseDto> doGuestOrder(List<BookListRequestDto> dtos) {
        return orderClient.doGuestOrder(dtos);
    }

    @Transactional
    public List<BookListResponseDto> doMemberOrder(Long userId, List<BookListRequestDto> dtos) {
        return orderClient.doMemberOrder(userId, dtos);
    }

    public TotalResponseDto calculateTotal(TotalRequestDto dtos) {
        return orderClient.calculateTotal(dtos);
    }

    public PageResponse<OrderHistoryResponseDto> getOrderHistory(Long userId, int page, int size) {

        return orderHistoryClient.getOrders(userId, page, size);
    }

    // 주문 상세 내역
    public OrderDetailResponseDto orderDetailHistory(Long userId, String orderNumber) {
        ResponseEntity<OrderDetailResponseDto> orderDetailDto = orderDetailClient.getOrderDetailHistory(userId, orderNumber);
        return orderDetailDto.getBody();
    }


    public OrderDetailResponseDto guestOrderDetail(String orderNumber, String email) {
        return orderDetailClient.guestOrderDetail(orderNumber, email);
    }

    // 결제 취소 신청
    public void applyCanceled(String orderNumber, Long userId, String canceledReason) {
        orderCanceledClient.applyCanceled(orderNumber, userId, canceledReason);
    }
}
