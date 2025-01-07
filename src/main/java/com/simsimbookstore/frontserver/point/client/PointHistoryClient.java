package com.simsimbookstore.frontserver.point.client;

import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PointHistory-api-server", url = "http://localhost:8000/api/shop/points")
public interface PointHistoryClient {

    @GetMapping("/{userId}")
    BigDecimal getUserPoints(@PathVariable("userId") Long userId);

}
