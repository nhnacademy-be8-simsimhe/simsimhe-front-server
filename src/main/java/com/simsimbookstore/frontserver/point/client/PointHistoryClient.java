package com.simsimbookstore.frontserver.point.client;

import com.simsimbookstore.frontserver.point.dto.PointHistoryResponseDto;
import com.simsimbookstore.frontserver.point.dto.PointPolicyResponseDto;
import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PointHistory-api-server", url = "http://localhost:8000/api/shop/points")
public interface PointHistoryClient {

    @GetMapping("/{userId}")
    BigDecimal getUserPoints(@PathVariable("userId") Long userId);

    @GetMapping("/history")
    Page<PointHistoryResponseDto> getPointHistory(@RequestParam("userId") Long userId, @RequestParam("page") int page,
                                                  @RequestParam("size") int size);

}
