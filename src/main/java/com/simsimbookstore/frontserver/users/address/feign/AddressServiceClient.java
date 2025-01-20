package com.simsimbookstore.frontserver.users.address.feign;

import com.simsimbookstore.frontserver.config.AuthenticationFeignConfig;
import com.simsimbookstore.frontserver.users.address.dto.AddressRequestDto;
import com.simsimbookstore.frontserver.users.address.dto.AddressResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "addressApi", url = "http://localhost:8000/api/users", configuration = AuthenticationFeignConfig.class)
public interface AddressServiceClient {

    @GetMapping("/{userId}/addresses")
    List<AddressResponseDto> getAddresses(@PathVariable Long userId);

    @PostMapping("/{userId}/addresses")
    AddressResponseDto addAddress(@PathVariable Long userId, @RequestBody AddressRequestDto addressRequestDto);

    @DeleteMapping("/addresses/{addressId}")
    String deleteAddress(@PathVariable Long addressId);
}
