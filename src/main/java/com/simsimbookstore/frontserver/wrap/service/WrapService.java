package com.simsimbookstore.frontserver.wrap.service;


import com.simsimbookstore.frontserver.wrap.client.WrapClient;
import com.simsimbookstore.frontserver.wrap.dto.WrapTypeRequestDto;
import com.simsimbookstore.frontserver.wrap.dto.WrapTypeResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WrapService {
    private final WrapClient wrapClient;


    public WrapTypeResponseDto getWrapType(Long id) {
        return wrapClient.getWrapTypeId(id);
    }

    public List<WrapTypeResponseDto> getAllWrapTypes() {
        return wrapClient.getAllWrapTypes();
    }

    public WrapTypeResponseDto createWrapType(WrapTypeRequestDto requestDto) {
        return wrapClient.createWrapType(requestDto);
    }

    public WrapTypeResponseDto updateWrapType(Long id, Boolean isAvailable) {
        return wrapClient.updateAvailability(id, isAvailable);
    }
}
