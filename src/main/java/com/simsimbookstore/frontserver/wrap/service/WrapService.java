package com.simsimbookstore.frontserver.wrap.service;


import com.simsimbookstore.frontserver.wrap.client.WrapAdminClient;
import com.simsimbookstore.frontserver.wrap.client.WrapOrderClient;
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
    private final WrapAdminClient wrapAdminClient;
    private final WrapOrderClient wrapOrderClient;

    public WrapTypeResponseDto getWrapType(Long id) {
        return wrapAdminClient.getWrapTypeId(id);
    }

    public List<WrapTypeResponseDto> getAllWrapTypes() {
        return wrapAdminClient.getAllWrapTypes();
    }

    public WrapTypeResponseDto createWrapType(WrapTypeRequestDto requestDto) {
        return wrapAdminClient.createWrapType(requestDto);
    }

    public WrapTypeResponseDto updateWrapType(Long id, Boolean isAvailable) {
        return wrapAdminClient.updateAvailability(id, isAvailable);
    }

    public List<WrapTypeResponseDto> getAllAvailableWrapTypes() {
        return wrapOrderClient.findAll();
    }
}
