package com.simsimbookstore.frontserver.order.dto;

import com.simsimbookstore.frontserver.pacakges.dto.PackageResponseDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookResponseDto {

    private Long orderBookId;
    private String bookTitle;
    private Integer quantity;
    private BigDecimal salePrice;
    private BigDecimal discountPrice;
    private String orderBookState;

    private List<PackageResponseDto> packages; // 패키지 정보
    private CouponDiscountResponseDto couponDiscount; // 쿠폰 할인 정보
}