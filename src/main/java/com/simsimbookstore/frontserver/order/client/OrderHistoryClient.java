package com.simsimbookstore.frontserver.order.client;

import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-history-api-server", url = "http://localhost:8000/api/shop/users")
public interface OrderHistoryClient {
    @GetMapping("{userId}/orders")
    Page<OrderHistoryResponseDto> getOrders(@PathVariable Long userId, Pageable pageable);
}
