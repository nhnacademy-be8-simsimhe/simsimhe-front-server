package com.simsimbookstore.frontserver.point.service;

import com.simsimbookstore.frontserver.point.client.PointHistoryClient;
import com.simsimbookstore.frontserver.point.dto.PointHistoryResponseDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointHistoryService {

    private final PointHistoryClient pointHistoryClient;

    public BigDecimal getPoints(Long userId) {
        return pointHistoryClient.getUserPoints(userId);
    }

    public PageResponse<PointHistoryResponseDto> getPointHistory (Long userId, int page, int size) {
        return pointHistoryClient.getPointHistory(userId, page, size);
    }

    public Long doEarnReviewPoint(Long userId, Long reviewId) {
        return pointHistoryClient.earnReviewPoint(userId, reviewId);
    }
}
