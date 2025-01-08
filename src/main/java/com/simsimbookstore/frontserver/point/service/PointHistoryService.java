package com.simsimbookstore.frontserver.point.service;

import com.simsimbookstore.frontserver.point.client.PointHistoryClient;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
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
}
