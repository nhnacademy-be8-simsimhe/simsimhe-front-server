package com.simsimbookstore.frontserver.order.client;


import com.simsimbookstore.frontserver.config.AuthenticationFeignConfig;
import com.simsimbookstore.frontserver.order.dto.BookListRequestDto;
import com.simsimbookstore.frontserver.order.dto.BookListResponseDto;
import com.simsimbookstore.frontserver.order.dto.TotalRequestDto;
import com.simsimbookstore.frontserver.order.dto.TotalResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-api-server", url = "http://localhost:8000/api/shop/order", configuration = AuthenticationFeignConfig.class)
public interface OrderClient {

    @PostMapping
    List<BookListResponseDto> doGuestOrder(@RequestBody List<BookListRequestDto> requestDtos);

    @PostMapping("/{userId}")
    List<BookListResponseDto> doMemberOrder(@PathVariable Long userId, @RequestBody List<BookListRequestDto> requestDtos);

    @PostMapping("/total")
    TotalResponseDto calculateTotal(@RequestBody TotalRequestDto requestDtos);


}
