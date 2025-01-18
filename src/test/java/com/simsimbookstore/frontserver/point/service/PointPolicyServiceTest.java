package com.simsimbookstore.frontserver.point.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.simsimbookstore.frontserver.point.client.PointPolicyClient;
import com.simsimbookstore.frontserver.point.dto.PointPolicy;

import com.simsimbookstore.frontserver.point.dto.PointPolicyRequestDto;
import com.simsimbookstore.frontserver.point.dto.PointPolicyResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PointPolicyServiceTest {

    @Mock
    private PointPolicyClient pointPolicyClient;

    @InjectMocks
    private PointPolicyService pointPolicyService;

    PointPolicyRequestDto mockRequestDto;
    PointPolicyResponseDto mockResponseDto;

    @BeforeEach
    void setUp() {
        mockResponseDto = PointPolicyResponseDto.builder()
                .pointPolicyId(1L)
                .earningMethod(PointPolicy.EarningMethod.REVIEW)
                .earningValue(BigDecimal.valueOf(100))
                .description("Review Points")
                .available(true)
                .earningType(PointPolicy.EarningType.FIX)
                .build();

        mockRequestDto = PointPolicyRequestDto.builder()
                .earningMethod(PointPolicy.EarningMethod.REVIEW)
                .earningValue(BigDecimal.valueOf(100))
                .description("Review Points")
                .available(true)
                .earningType(PointPolicy.EarningType.FIX)
                .build();
    }

    @Test
    @DisplayName("getAll() - 모든 포인트 정책 조회")
    void testGetAll() {
        when(pointPolicyClient.getAll()).thenReturn(List.of(mockResponseDto));

        List<PointPolicyResponseDto> result = pointPolicyService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pointPolicyClient, times(1)).getAll();
    }

    @Test
    @DisplayName("create() - 포인트 정책 생성")
    void testCreate() {
        when(pointPolicyClient.create(any(PointPolicyRequestDto.class))).thenReturn(mockResponseDto);

        PointPolicyResponseDto result = pointPolicyService.create(mockRequestDto);

        assertNotNull(result);
        assertEquals(mockResponseDto.getPointPolicyId(), result.getPointPolicyId());
        verify(pointPolicyClient, times(1)).create(any(PointPolicyRequestDto.class));
    }

    @Test
    @DisplayName("update() - 포인트 정책 업데이트")
    void testUpdate() {
        Long policyId = 1L;
        when(pointPolicyClient.updatePolicy(eq(policyId), any(PointPolicyRequestDto.class))).thenReturn(mockResponseDto);

        PointPolicyResponseDto result = pointPolicyService.update(policyId, mockRequestDto);

        assertNotNull(result);
        assertEquals(mockResponseDto.getPointPolicyId(), result.getPointPolicyId());
        verify(pointPolicyClient, times(1)).updatePolicy(eq(policyId), any(PointPolicyRequestDto.class));
    }

    @Test
    @DisplayName("delete() - 포인트 정책 삭제")
    void testDelete() {
        Long policyId = 1L;
        doNothing().when(pointPolicyClient).deletePolicy(policyId);

        pointPolicyService.delete(policyId);

        verify(pointPolicyClient, times(1)).deletePolicy(policyId);
    }

    @Test
    @DisplayName("getById() - 특정 포인트 정책 조회")
    void testGetById() {
        Long policyId = 1L;
        when(pointPolicyClient.getById(policyId)).thenReturn(mockResponseDto);

        PointPolicyResponseDto result = pointPolicyService.getById(policyId);

        assertNotNull(result);
        assertEquals(mockResponseDto.getPointPolicyId(), result.getPointPolicyId());
        verify(pointPolicyClient, times(1)).getById(policyId);
    }
}