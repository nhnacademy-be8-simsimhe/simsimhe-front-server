package com.simsimbookstore.frontserver.order.service;

import com.simsimbookstore.frontserver.order.client.OrderAdminClient;
import com.simsimbookstore.frontserver.order.dto.OrderAdminResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderBookResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderBookStateUpdateRequestDto;
import com.simsimbookstore.frontserver.order.dto.OrderOrderBookResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderStateUpdateDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderAdminService {

    private final OrderAdminClient orderAdminClient;


    public PageResponse<OrderResponseDto> getOrderBooks(int page, int size) {
        return orderAdminClient.getOrderBooks(page, size);
    }

    public OrderOrderBookResponseDto getOrderOrderBook(Long orderId) {
        return orderAdminClient.getOrderOrderBook(orderId);
    }

    public OrderBookResponseDto updateOrderBook(Long orderBookId, OrderBookStateUpdateRequestDto dto) {
        return orderAdminClient.updateOrderBookState(orderBookId, dto);
    }

    public OrderAdminResponseDto updateOrderState(Long orderId,
                                                  OrderStateUpdateDto dto) {
        return orderAdminClient.updateOrderState(orderId, dto);
    }


}