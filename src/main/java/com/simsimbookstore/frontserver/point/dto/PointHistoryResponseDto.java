package com.simsimbookstore.frontserver.point.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryResponseDto {
    private PointType pointType;
    private Integer amount;
    private LocalDateTime createdAt;
    private String sourceType;
    private Long orderId;
    private Long reviewId;//review_id OR order_id
    private String description;
}

