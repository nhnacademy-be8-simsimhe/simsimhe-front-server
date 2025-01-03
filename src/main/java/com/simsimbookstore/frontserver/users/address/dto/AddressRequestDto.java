package com.simsimbookstore.frontserver.users.address.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequestDto {
    @NotNull
    @Length(min = 1, max = 20)
    private String alias;

    @NotNull
    @Length(min = 5, max = 5)
    private String postalCode;

    @NotNull
    @Length(min = 5, max = 255)
    private String roadAddress;

    @Length(min = 0, max = 255)
    private String detailedAddress;
}
