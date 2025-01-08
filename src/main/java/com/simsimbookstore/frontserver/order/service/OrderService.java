package com.simsimbookstore.frontserver.order.service;

import com.simsimbookstore.frontserver.order.client.OrderClient;
import com.simsimbookstore.frontserver.order.client.OrderHistoryClient;
import com.simsimbookstore.frontserver.order.dto.BookListRequestDto;
import com.simsimbookstore.frontserver.order.dto.BookListResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import com.simsimbookstore.frontserver.order.dto.TotalRequestDto;
import com.simsimbookstore.frontserver.order.dto.TotalResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderClient orderClient;
    private final OrderHistoryClient orderHistoryClient;

    @Transactional
    public List<BookListResponseDto> doOrder(List<BookListRequestDto> dtos) {
        return orderClient.doOrder(dtos);
    }

    public TotalResponseDto calculateTotal(TotalRequestDto dtos) {
        return orderClient.calculateTotal(dtos);
    }

    public Page<OrderHistoryResponseDto> getOrderHistory(Long userId, Pageable pageable) {
        return orderHistoryClient.getOrders(userId, pageable);
    }
}
