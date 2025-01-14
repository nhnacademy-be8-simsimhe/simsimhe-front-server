package com.simsimbookstore.frontserver.delivery.policy.client;

import com.simsimbookstore.frontserver.delivery.policy.dto.Delivery;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryDetailResponseDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryResponseDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryStateUpdateRequestDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryTrackingNumberRequestDto;
import com.simsimbookstore.frontserver.order.dto.DeliveryRequestDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "deliveryAdminClient", url = "http://localhost:8000/api/admin/deliveries")
public interface DeliveryAdminClient {

    @GetMapping("/{id}")
    DeliveryDetailResponseDto findById(@PathVariable("id") Long id);

    @PostMapping("/{id}/state")
    DeliveryResponseDto updateState(@PathVariable("id") Long id,
                                    @RequestBody DeliveryStateUpdateRequestDto requestDto);

    @GetMapping("/tracking-numbers/{id}")
    DeliveryResponseDto findByTrackingNumber(@PathVariable("id") Integer id);

    @PostMapping("/{id}")
    DeliveryResponseDto updateDelivery(@PathVariable("id") Long id,
                                       @RequestBody DeliveryRequestDto requestDto);

    @GetMapping
    PageResponse<DeliveryResponseDto> findAll(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "15") int size);

    @GetMapping("/state")
    PageResponse<DeliveryResponseDto> getDeliveriesByState(@RequestParam("state") Delivery.DeliveryState state,
                                                           @RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "15") int size);

    @PostMapping("/{id}/tracking-number")
    DeliveryResponseDto updateTrackingNumber(@PathVariable("id") Long deliveryId,
                                             @RequestBody DeliveryTrackingNumberRequestDto requestDto);
}

