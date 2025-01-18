package com.simsimbookstore.frontserver.delivery.policy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.simsimbookstore.frontserver.delivery.policy.client.DeliveryPolicyClient;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicy;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicyRequestDto;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class DeliveryPolicyServiceTest {

    @Mock
    private DeliveryPolicyClient deliveryPolicyClient;

    @InjectMocks
    private DeliveryPolicyService deliveryPolicyService;

    private DeliveryPolicy mockPolicy;

    @BeforeEach
    void setUp() {
        mockPolicy = new DeliveryPolicy(
                1L,
                "Standard Delivery",
                BigDecimal.valueOf(5000),
                BigDecimal.valueOf(20000),
                true
        );
    }

    @Test
    @DisplayName("findAll() - 모든 배송 정책 조회 성공")
    void testFindAll() {
        // Given
        List<DeliveryPolicy> mockPolicies = Arrays.asList(
                mockPolicy,
                new DeliveryPolicy(2L, "Express Delivery", BigDecimal.valueOf(10000), BigDecimal.valueOf(50000), false)
        );
        when(deliveryPolicyClient.getAllDeliveryPolices()).thenReturn(mockPolicies);

        // When
        List<DeliveryPolicy> result = deliveryPolicyService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Standard Delivery", result.get(0).getDeliveryPolicyName());
        assertEquals(BigDecimal.valueOf(5000), result.get(0).getDeliveryPrice());
        verify(deliveryPolicyClient, times(1)).getAllDeliveryPolices();
    }

    @Test
    @DisplayName("createDeliveryPolicy() - 배송 정책 생성 성공")
    void testCreateDeliveryPolicy() {
        // Given
        DeliveryPolicyRequestDto requestDto = new DeliveryPolicyRequestDto(
                "Same Day Delivery",
                BigDecimal.valueOf(50000),
                true,
                BigDecimal.valueOf(15000)
        );

        DeliveryPolicy createdPolicy = new DeliveryPolicy(
                3L,
                "Same Day Delivery",
                BigDecimal.valueOf(15000),
                BigDecimal.valueOf(50000),
                true
        );

        when(deliveryPolicyClient.createDeliveryPolicy(requestDto)).thenReturn(createdPolicy);

        // When
        DeliveryPolicy result = deliveryPolicyService.createDeliveryPolicy(requestDto);

        // Then
        assertNotNull(result);
        assertEquals("Same Day Delivery", result.getDeliveryPolicyName());
        assertEquals(BigDecimal.valueOf(15000), result.getDeliveryPrice());
        verify(deliveryPolicyClient, times(1)).createDeliveryPolicy(requestDto);
    }

    @Test
    @DisplayName("toggleDeliveryPolicy() - 배송 정책 활성/비활성 전환 성공")
    void testToggleDeliveryPolicy() {
        // Given
        Long policyId = 1L;

        // When
        deliveryPolicyService.toggleDeliveryPolicy(policyId);

        // Then
        verify(deliveryPolicyClient, times(1)).toggleStandardPolicy(policyId);
    }

    @Test
    @DisplayName("deleteDeliveryPolicy() - 배송 정책 삭제 성공")
    void testDeleteDeliveryPolicy() {
        // Given
        Long policyId = 1L;

        // When
        deliveryPolicyService.deleteDeliveryPolicy(policyId);

        // Then
        verify(deliveryPolicyClient, times(1)).deleteDeliveryPolicy(policyId);
    }
}


