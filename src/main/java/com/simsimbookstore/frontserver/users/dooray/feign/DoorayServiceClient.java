package com.simsimbookstore.frontserver.users.dooray.feign;

import com.simsimbookstore.frontserver.users.dooray.dto.DoorayMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.*;

@FeignClient(name = "dooray-api",url = "https://nhnacademy.dooray.com/services/3204376758577275363/3977015669102895002/7nF92h5_Q5yX9-r710Q4Mg")
public interface DoorayServiceClient {

    @PostMapping()
    void sendMessage(@RequestBody DoorayMessageDto doorayMessageDto);
}
