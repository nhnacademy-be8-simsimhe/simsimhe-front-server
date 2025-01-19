package com.simsimbookstore.frontserver.wrap.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.simsimbookstore.frontserver.wrap.client.WrapAdminClient;
import com.simsimbookstore.frontserver.wrap.client.WrapOrderClient;
import com.simsimbookstore.frontserver.wrap.dto.WrapTypeRequestDto;
import com.simsimbookstore.frontserver.wrap.dto.WrapTypeResponseDto;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WrapServiceTest {

    @Mock
    private WrapAdminClient wrapAdminClient;

    @Mock
    private WrapOrderClient wrapOrderClient;

    @InjectMocks
    private WrapService wrapService;

    private WrapTypeResponseDto sampleResponseDto;
    private WrapTypeRequestDto sampleRequestDto;

    @BeforeEach
    void setUp() {
        sampleResponseDto = WrapTypeResponseDto.builder()
                .packageTypeId(1L)
                .packageName("Sample Wrap")
                .packagePrice(new BigDecimal("100.00"))
                .isAvailable(true)
                .build();

        sampleRequestDto = new WrapTypeRequestDto();
        sampleRequestDto.setPackageName("New Wrap");
        sampleRequestDto.setPackagePrice(new BigDecimal("50.00"));
        sampleRequestDto.setIsAvailable(true);
    }

    @Test
    @DisplayName("getWrapType() - 특정 포장 타입 조회")
    void testGetWrapType() {
        Long id = 1L;
        when(wrapAdminClient.getWrapTypeId(id)).thenReturn(sampleResponseDto);

        WrapTypeResponseDto result = wrapService.getWrapType(id);

        assertNotNull(result);
        assertEquals(id, result.getPackageTypeId());
        verify(wrapAdminClient, times(1)).getWrapTypeId(id);
    }

    @Test
    @DisplayName("getAllWrapTypes() - 모든 포장 타입 조회")
    void testGetAllWrapTypes() {
        List<WrapTypeResponseDto> list = List.of(sampleResponseDto);
        when(wrapAdminClient.getAllWrapTypes()).thenReturn(list);

        List<WrapTypeResponseDto> result = wrapService.getAllWrapTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(wrapAdminClient, times(1)).getAllWrapTypes();
    }

    @Test
    @DisplayName("createWrapType() - 포장 타입 생성")
    void testCreateWrapType() {
        when(wrapAdminClient.createWrapType(sampleRequestDto)).thenReturn(sampleResponseDto);

        WrapTypeResponseDto result = wrapService.createWrapType(sampleRequestDto);

        assertNotNull(result);
        assertEquals(sampleResponseDto.getPackageName(), result.getPackageName());
        verify(wrapAdminClient, times(1)).createWrapType(sampleRequestDto);
    }

    @Test
    @DisplayName("updateWrapType() - 포장 타입 업데이트")
    void testUpdateWrapType() {
        Long id = 1L;
        Boolean newAvailability = false;
        WrapTypeResponseDto updatedResponse = WrapTypeResponseDto.builder()
                .packageTypeId(id)
                .packageName("Updated Wrap")
                .packagePrice(new BigDecimal("100.00"))
                .isAvailable(newAvailability)
                .build();

        when(wrapAdminClient.updateAvailability(id, newAvailability)).thenReturn(updatedResponse);

        WrapTypeResponseDto result = wrapService.updateWrapType(id, newAvailability);

        assertNotNull(result);
        assertEquals(newAvailability, result.getIsAvailable());
        verify(wrapAdminClient, times(1)).updateAvailability(id, newAvailability);
    }

    @Test
    @DisplayName("getAllAvailableWrapTypes() - 사용 가능한 모든 포장 타입 조회")
    void testGetAllAvailableWrapTypes() {
        List<WrapTypeResponseDto> list = List.of(sampleResponseDto);
        when(wrapOrderClient.findAll()).thenReturn(list);

        List<WrapTypeResponseDto> result = wrapService.getAllAvailableWrapTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(wrapOrderClient, times(1)).findAll();
    }
}
