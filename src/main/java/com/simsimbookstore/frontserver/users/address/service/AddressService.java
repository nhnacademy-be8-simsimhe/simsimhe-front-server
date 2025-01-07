package com.simsimbookstore.frontserver.users.address.service;

import com.simsimbookstore.frontserver.users.address.dto.AddressRequestDto;
import com.simsimbookstore.frontserver.users.address.dto.AddressResponseDto;

import java.util.List;

public interface AddressService {
    List<AddressResponseDto> getAddress(Long userId);

    AddressResponseDto addAddress(Long userId, AddressRequestDto addressRequestDto);

    void deleteAddress(Long addressId);
}
