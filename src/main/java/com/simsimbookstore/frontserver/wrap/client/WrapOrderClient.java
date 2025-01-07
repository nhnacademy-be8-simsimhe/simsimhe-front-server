package com.simsimbookstore.frontserver.wrap.client;

import com.simsimbookstore.frontserver.wrap.dto.WrapTypeResponseDto;
import java.util.List;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "wrap-order-api-server", url = "http://localhost:8000/api/shop/wrap-types")
public interface WrapOrderClient {

    @GetMapping
    List<WrapTypeResponseDto> findAll();

}
