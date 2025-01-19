package com.simsimbookstore.frontserver.point.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.simsimbookstore.frontserver.point.dto.PointHistoryResponseDto;
import com.simsimbookstore.frontserver.point.client.PointHistoryClient;
import com.simsimbookstore.frontserver.point.dto.PointType;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PointHistoryServiceTest {

    @Mock
    private PointHistoryClient pointHistoryClient;

    @InjectMocks
    private PointHistoryService pointHistoryService;

    private PointHistoryResponseDto mockResponseDto;

    @BeforeEach
    void setUp() {
        mockResponseDto = PointHistoryResponseDto.builder()
                .pointType(PointType.EARN)
                .amount(100)
                .createdAt(LocalDateTime.now())
                .sourceType("REVIEW")
                .reviewId(1L)
                .description("Review Earned Points")
                .build();
    }

    @Test
    @DisplayName("getPoints() - 특정 유저의 포인트 조회")
    void testGetPoints() {
        // Given
        Long userId = 1L;
        BigDecimal expectedPoints = BigDecimal.valueOf(5000);
        when(pointHistoryClient.getUserPoints(userId)).thenReturn(expectedPoints);

        // When
        BigDecimal points = pointHistoryService.getPoints(userId);

        // Then
        assertNotNull(points);
        assertEquals(expectedPoints, points);
        verify(pointHistoryClient, times(1)).getUserPoints(userId);
    }

    @Test
    @DisplayName("getPointHistory() - 특정 유저의 포인트 히스토리 조회")
    void testGetPointHistory() {
        // Given
        Long userId = 1L;
        int page = 1;
        int size = 10;
        PageResponse<PointHistoryResponseDto> expectedResponse = PageResponse.<PointHistoryResponseDto>builder()
                .data(List.of(mockResponseDto))
                .currentPage(page)
                .totalPage(5)
                .totalElements(50L)
                .build();
        when(pointHistoryClient.getPointHistory(userId, page, size)).thenReturn(expectedResponse);

        // When
        PageResponse<PointHistoryResponseDto> history = pointHistoryService.getPointHistory(userId, page, size);

        // Then
        assertNotNull(history);
        assertEquals(1, history.getData().size());
        assertEquals(page, history.getCurrentPage());
        assertEquals(5, history.getTotalPage());
        verify(pointHistoryClient, times(1)).getPointHistory(userId, page, size);
    }

    @Test
    @DisplayName("doEarnReviewPoint() - 리뷰 포인트 적립")
    void testDoEarnReviewPoint() {
        // Given
        Long userId = 1L;
        Long reviewId = 2L;
        Long expectedEarnedId = 10L;
        when(pointHistoryClient.earnReviewPoint(userId, reviewId)).thenReturn(expectedEarnedId);

        // When
        Long earnedId = pointHistoryService.doEarnReviewPoint(userId, reviewId);

        // Then
        assertNotNull(earnedId);
        assertEquals(expectedEarnedId, earnedId);
        verify(pointHistoryClient, times(1)).earnReviewPoint(userId, reviewId);
    }

    @Test
    @DisplayName("getEarnValue() - 특정 유저의 적립 정책 조회")
    void testGetEarnValue() {
        // Given
        Long userId = 1L;
        BigDecimal expectedEarnValue = BigDecimal.valueOf(1000);
        when(pointHistoryClient.getUserPointPolicy(userId)).thenReturn(expectedEarnValue);

        // When
        BigDecimal earnValue = pointHistoryService.getEarnValue(userId);

        // Then
        assertNotNull(earnValue);
        assertEquals(expectedEarnValue, earnValue);
        verify(pointHistoryClient, times(1)).getUserPointPolicy(userId);
    }
}