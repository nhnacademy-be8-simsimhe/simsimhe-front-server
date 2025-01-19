package com.simsimbookstore.frontserver.delivery.policy.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.simsimbookstore.frontserver.delivery.policy.dto.Delivery;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryDetailResponseDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryResponseDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryStateUpdateRequestDto;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryTrackingNumberRequestDto;
import com.simsimbookstore.frontserver.util.PageResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeliveryAdminClientTest {

    @Mock
    private DeliveryAdminClient deliveryAdminClient;



    @Test
    @DisplayName("findById() - 특정 배송 ID 조회")
    void testFindById() {
        // Given
        Long deliveryId = 1L;
        DeliveryDetailResponseDto mockResponse = DeliveryDetailResponseDto.builder()
                .deliveryId(deliveryId)
                .deliveryState("READY")
                .deliveryReceiver("John Doe")
                .receiverPhoneNumber("010-1234-5678")
                .build();

        when(deliveryAdminClient.findById(deliveryId)).thenReturn(mockResponse);

        // When
        DeliveryDetailResponseDto response = deliveryAdminClient.findById(deliveryId);

        // Then
        assertNotNull(response);
        assertEquals(deliveryId, response.getDeliveryId());
        assertEquals("READY", response.getDeliveryState());
        verify(deliveryAdminClient, times(1)).findById(deliveryId);
    }

    @Test
    @DisplayName("updateState() - 배송 상태 업데이트")
    void testUpdateState() {
        // Given
        Long deliveryId = 1L;
        DeliveryStateUpdateRequestDto requestDto = DeliveryStateUpdateRequestDto.builder()
                .newState(Delivery.DeliveryState.COMPLETED)
                .build();

        DeliveryResponseDto mockResponse = DeliveryResponseDto.builder()
                .deliveryId(deliveryId)
                .deliveryState(String.valueOf(Delivery.DeliveryState.COMPLETED))
                .build();

        when(deliveryAdminClient.updateState(deliveryId, requestDto)).thenReturn(mockResponse);

        // When
        DeliveryResponseDto response = deliveryAdminClient.updateState(deliveryId, requestDto);

        // Then
        assertNotNull(response);
        assertEquals("COMPLETED", response.getDeliveryState());
        verify(deliveryAdminClient, times(1)).updateState(deliveryId, requestDto);
    }

    @Test
    @DisplayName("findByTrackingNumber() - 특정 운송장 번호로 조회")
    void testFindByTrackingNumber() {
        // Given
        Integer trackingNumber = 123456;
        DeliveryResponseDto mockResponse = DeliveryResponseDto.builder()
                .deliveryId(1L)
                .trackingNumber(trackingNumber)
                .deliveryState("IN_PROGRESS")
                .build();

        when(deliveryAdminClient.findByTrackingNumber(trackingNumber)).thenReturn(mockResponse);

        // When
        DeliveryResponseDto response = deliveryAdminClient.findByTrackingNumber(trackingNumber);

        // Then
        assertNotNull(response);
        assertEquals(trackingNumber, response.getTrackingNumber());
        assertEquals("IN_PROGRESS", response.getDeliveryState());
        verify(deliveryAdminClient, times(1)).findByTrackingNumber(trackingNumber);
    }

    @Test
    @DisplayName("findAll() - 모든 배송 조회")
    void testFindAll() {
        // Given
        PageResponse<DeliveryResponseDto> mockResponse = PageResponse.<DeliveryResponseDto>builder()
                .data(List.of(
                        DeliveryResponseDto.builder()
                                .deliveryId(1L)
                                .deliveryState("READY")
                                .deliveryReceiver("John Doe")
                                .build(),
                        DeliveryResponseDto.builder()
                                .deliveryId(2L)
                                .deliveryState("IN_PROGRESS")
                                .deliveryReceiver("Jane Doe")
                                .build()
                ))
                .currentPage(1)
                .totalPage(5)
                .build();

        when(deliveryAdminClient.findAll(1, 15)).thenReturn(mockResponse);

        // When
        PageResponse<DeliveryResponseDto> response = deliveryAdminClient.findAll(1, 15);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getData().size());
        assertEquals(1, response.getCurrentPage());
        assertEquals(5, response.getTotalPage());
        verify(deliveryAdminClient, times(1)).findAll(1, 15);
    }

    @Test
    @DisplayName("updateTrackingNumber() - 운송장 번호 업데이트")
    void testUpdateTrackingNumber() {
        // Given
        Long deliveryId = 1L;
        DeliveryTrackingNumberRequestDto requestDto = DeliveryTrackingNumberRequestDto.builder()
                .trackingNumber(123456)
                .build();

        DeliveryResponseDto mockResponse = DeliveryResponseDto.builder()
                .deliveryId(deliveryId)
                .trackingNumber(123456)
                .build();

        when(deliveryAdminClient.updateTrackingNumber(deliveryId, requestDto)).thenReturn(mockResponse);

        // When
        DeliveryResponseDto response = deliveryAdminClient.updateTrackingNumber(deliveryId, requestDto);

        // Then
        assertNotNull(response);
        assertEquals(123456, response.getTrackingNumber());
        verify(deliveryAdminClient, times(1)).updateTrackingNumber(deliveryId, requestDto);
    }
}
