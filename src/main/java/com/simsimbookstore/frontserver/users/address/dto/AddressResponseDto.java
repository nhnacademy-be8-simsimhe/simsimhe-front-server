package com.simsimbookstore.frontserver.users.address.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddressResponseDto {
    private Long addressId;

    private String alias;

    private String postalCode;

    private String roadAddress;

    private String detailedAddress;
}
