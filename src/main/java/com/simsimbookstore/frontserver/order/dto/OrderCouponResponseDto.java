package com.simsimbookstore.frontserver.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.coyote.BadRequestException;

@Getter
@AllArgsConstructor
public class OrderCouponResponseDto {
    private Long couponId;
    private String couponTypeName;
    private DisCountType discountType;

    enum DisCountType {
        RATE("정률"),
        FIX("정액");

        private final String name;

        DisCountType(String name) {
            this.name = name;
        }
        @JsonCreator
        public static DisCountType from(String value) {
            for (DisCountType disCountType : values()) {
                if (disCountType.name.equals(value)) {
                    return disCountType;
                }
            }
            throw new IllegalArgumentException("잘못된 DiscountType입니다. '정률'과'정액'중 하나를 선택하세요");
        }
        // 직렬화 : Enum -> JSON
        @JsonValue
        public String getName() {
            return name;
        }
    }
}