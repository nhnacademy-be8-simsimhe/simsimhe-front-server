package com.simsimbookstore.frontserver.wrap.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import com.simsimbookstore.frontserver.security.provider.TokenProvider;
import com.simsimbookstore.frontserver.wrap.dto.WrapTypeRequestDto;
import com.simsimbookstore.frontserver.wrap.dto.WrapTypeResponseDto;
import com.simsimbookstore.frontserver.wrap.service.WrapService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WrapTypeAdminController.class)
@Import(WrapTypeAdminControllerTest.MockConfig.class)
class WrapTypeAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().permitAll() // 모든 요청 허용
                    );
            return http.build();
        }
        @Bean
        @Primary
        public WrapService wrapService() {
            return mock(WrapService.class);
        }
//        @Bean
//        public TokenProvider tokenProvider() {
//            return mock(TokenProvider.class); // 필요 시 Mock 구현
//        }
    }

    @Autowired
    private WrapService wrapService;

    @Test
    @DisplayName("GET /admin/wrap-types - 모든 포장지 조회")
    void testGetAllWrapTypes() throws Exception {
        WrapTypeResponseDto wrapType = WrapTypeResponseDto.builder()
                .packageTypeId(1L)
                .packageName("Sample Wrap")
                .packagePrice(new BigDecimal("100.00"))
                .isAvailable(true)
                .build();
        when(wrapService.getAllWrapTypes()).thenReturn(List.of(wrapType));

        mockMvc.perform(get("/admin/wrap-types"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/wrap/wrapTypeList"))
                .andExpect(model().attributeExists("wrapTypes"));

        verify(wrapService, times(1)).getAllWrapTypes();
    }

    @Test
    @DisplayName("GET /admin/wrap-types/create - 포장지 추가 폼")
    void testCreateWrapTypeForm() throws Exception {
        mockMvc.perform(get("/admin/wrap-types/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/wrap/wrapTypeCreate"))
                .andExpect(model().attributeExists("wrapType"));
    }

    @Test
    @DisplayName("POST /admin/wrap-types/create - 포장지 추가")
    void testCreateWrapType() throws Exception {
        WrapTypeRequestDto requestDto = new WrapTypeRequestDto();
        requestDto.setPackageName("New Wrap");
        requestDto.setPackagePrice(new BigDecimal("50.00"));
        requestDto.setIsAvailable(true);

        WrapTypeResponseDto responseDto = WrapTypeResponseDto.builder()
                .packageTypeId(2L)
                .packageName("New Wrap")
                .packagePrice(new BigDecimal("50.00"))
                .isAvailable(true)
                .build();

        when(wrapService.createWrapType(any(WrapTypeRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/admin/wrap-types/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("packageName", requestDto.getPackageName())
                        .param("packagePrice", requestDto.getPackagePrice().toString())
                        .param("isAvailable", requestDto.getIsAvailable().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/wrap-types"));

        verify(wrapService, times(1)).createWrapType(any(WrapTypeRequestDto.class));
    }

    @Test
    @DisplayName("GET /admin/wrap-types/update/{id}/availability - 판매 가능 여부 업데이트 폼")
    void testUpdateAvailabilityForm() throws Exception {
        Long id = 1L;
        WrapTypeResponseDto wrapType = WrapTypeResponseDto.builder()
                .packageTypeId(id)
                .packageName("Sample Wrap")
                .packagePrice(new BigDecimal("100.00"))
                .isAvailable(true)
                .build();
        when(wrapService.getWrapType(id)).thenReturn(wrapType);

        mockMvc.perform(get("/admin/wrap-types/update/{id}/availability", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/wrap/updateAvailability"))
                .andExpect(model().attributeExists("wrapType"));

        verify(wrapService, times(1)).getWrapType(id);
    }

    @Test
    @DisplayName("POST /admin/wrap-types/update/{id}/availability - 판매 가능 여부 업데이트")
    void testUpdateAvailability() throws Exception {
        Long id = 1L;
        Boolean newAvailability = false;

        mockMvc.perform(post("/admin/wrap-types/update/{id}/availability", id)
                        .param("isAvailable", newAvailability.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/wrap-types"));

        verify(wrapService, times(1)).updateWrapType(id, newAvailability);
    }
}