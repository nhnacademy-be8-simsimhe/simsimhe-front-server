package com.simsimbookstore.frontserver.order.service;

import com.simsimbookstore.frontserver.order.client.OrderClient;
import com.simsimbookstore.frontserver.order.client.OrderDetailClient;
import com.simsimbookstore.frontserver.order.client.OrderHistoryClient;

import com.simsimbookstore.frontserver.order.dto.*;
import com.simsimbookstore.frontserver.util.PageResponse;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public OrderDetailResponseDto orderDetailHistory(Long userId, String orderNumber) {
        ResponseEntity<OrderDetailResponseDto> orderDetailDto = orderDetailClient.getOrderDetailHistory(userId, orderNumber);
        return orderDetailDto.getBody();
    }
}
