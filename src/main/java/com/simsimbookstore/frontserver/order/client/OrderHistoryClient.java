package com.simsimbookstore.frontserver.order.client;

import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-history-api-server", url = "http://localhost:8000/api/shop/users")
public interface OrderHistoryClient {
    @GetMapping("{userId}/orders")
    PageResponse<OrderHistoryResponseDto> getOrders(@PathVariable Long userId,
                                                    @RequestParam(defaultValue = "1") int page,// 페이지 번호
                                                    @RequestParam(defaultValue = "15") int size);
}
