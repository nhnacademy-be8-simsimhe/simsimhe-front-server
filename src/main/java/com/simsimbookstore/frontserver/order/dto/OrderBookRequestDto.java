package com.simsimbookstore.frontserver.order.dto;

import com.simsimbookstore.frontserver.pacakges.dto.PackageRequestDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookRequestDto {
    @Setter
    private Long orderId;
    private Long bookId;
    private Long couponId;
    private Integer quantity;
    private BigDecimal salePrice;
    private BigDecimal discountPrice;
    private String orderBookState;

    // (선택) 쿠폰 할인 정보 1개
    private CouponDiscountRequestDto couponDiscountRequestDto;

    // (선택) 패키지 목록
    private List<PackageRequestDto> packagesRequestDtos;

}