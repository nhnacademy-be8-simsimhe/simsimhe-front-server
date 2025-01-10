package com.simsimbookstore.frontserver.wrap.client;

import com.simsimbookstore.frontserver.wrap.dto.WrapTypeRequestDto;
import com.simsimbookstore.frontserver.wrap.dto.WrapTypeResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wrap-admin-api-server", url = "http://localhost:8000/api/admin/wrap-types")
public interface WrapAdminClient {

    @GetMapping("/{id}")
    WrapTypeResponseDto getWrapTypeId(@PathVariable("id") Long id);

    @PostMapping
    WrapTypeResponseDto createWrapType(@RequestBody @Valid WrapTypeRequestDto wrapTypeRequestDto);

    @GetMapping
    List<WrapTypeResponseDto> getAllWrapTypes();

    @PostMapping("/{id}/availability")
    WrapTypeResponseDto updateAvailability(
            @PathVariable Long id,
            @RequestParam Boolean isAvailable);
}
