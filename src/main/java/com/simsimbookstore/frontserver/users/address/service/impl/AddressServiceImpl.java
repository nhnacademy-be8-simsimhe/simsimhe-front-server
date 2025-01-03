package com.simsimbookstore.frontserver.users.address.service.impl;

import com.simsimbookstore.frontserver.users.address.dto.AddressRequestDto;
import com.simsimbookstore.frontserver.users.address.feign.AddressServiceClient;
import com.simsimbookstore.frontserver.users.address.dto.AddressResponseDto;
import com.simsimbookstore.frontserver.users.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressServiceClient addressServiceClient;

    @Override
    public List<AddressResponseDto> getAddress(Long userId) {
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        List<AddressResponseDto> addresses = addressServiceClient.getAddresses(userId);
        return addresses;
    }

    @Override
    public AddressResponseDto addAddress(Long userId, AddressRequestDto addressRequestDto) {
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (Objects.isNull(addressRequestDto)) {
            throw new IllegalArgumentException("addressRequestDto cannot be null");
        }

        AddressResponseDto addressResponseDto = addressServiceClient.addAddress(userId, addressRequestDto);
        return addressResponseDto;
    }

    @Override
    public void deleteAddress(Long addressId) {
        if (Objects.isNull(addressId)) {
            throw new IllegalArgumentException("addressRequestDto cannot be null");
        }

        addressServiceClient.deleteAddress(addressId);
    }
}
