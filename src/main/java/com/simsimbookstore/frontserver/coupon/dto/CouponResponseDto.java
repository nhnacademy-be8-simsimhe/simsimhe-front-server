package com.simsimbookstore.frontserver.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "disCountType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixCouponResponseDto.class, name = "정액"),
        @JsonSubTypes.Type(value = RateCouponResponseDto.class, name = "정률")
})
public abstract class CouponResponseDto {
    //쿠폰 Id
    private Long couponId;
    //쿠폰 발급 날짜
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime issueDate;
    // 쿠폰 마감일
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime deadline;
    // 쿠폰 상태 -> USED, EXPIRED, UNUSED
    private CouponStatus couponStatus;
    // 쿠폰 정책 이름
    private String couponTypeName;
    // 중복여부
    private boolean isStacking;
    // 쿠폰 적용 대상 -> ALL, CATEGORY, BOOK
    private CouponTargetType couponTargetType;
    // 쿠폰 적용 대상의 Id
    private Long couponTargetId; //추후 고민

    private DisCountType disCountType;

    private Long userId;
}
