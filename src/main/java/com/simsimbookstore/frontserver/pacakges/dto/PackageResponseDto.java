package com.simsimbookstore.frontserver.pacakges.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageResponseDto {
    private Long packageId;
    private String packageType;
}