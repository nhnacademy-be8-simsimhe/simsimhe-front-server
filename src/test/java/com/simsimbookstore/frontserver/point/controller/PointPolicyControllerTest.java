package com.simsimbookstore.frontserver.point.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simsimbookstore.frontserver.point.dto.PointPolicy;
import com.simsimbookstore.frontserver.point.dto.PointPolicyRequestDto;
import com.simsimbookstore.frontserver.point.dto.PointPolicyResponseDto;
import com.simsimbookstore.frontserver.point.service.PointPolicyService;
//import com.simsimbookstore.frontserver.security.provider.TokenProvider;
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

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(PointPolicyController.class)
@Import({PointPolicyControllerTest.MockConfig.class})
class PointPolicyControllerTest {

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
        public PointPolicyService pointPolicyService() {
            return mock(PointPolicyService.class);
        }

//        @Bean
//        public TokenProvider tokenProvider() {
//            return mock(TokenProvider.class);
//        }
    }

    @Autowired
    private PointPolicyService pointPolicyService;

    @Test
    @DisplayName("GET /admin/pointPolicies - 모든 포인트 정책 조회")
    void testGetAllPolicies() throws Exception {
        // Given
        PointPolicyResponseDto responseDto = PointPolicyResponseDto.builder()
                .pointPolicyId(1L)
                .earningMethod(PointPolicy.EarningMethod.REVIEW)
                .earningValue(BigDecimal.valueOf(100))
                .description("Review Points")
                .available(true)
                .earningType(PointPolicy.EarningType.FIX)
                .build();

        when(pointPolicyService.getAll()).thenReturn(List.of(responseDto));

        // When & Then
        mockMvc.perform(get("/admin/pointPolicies"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/point/policy/list"))
                .andExpect(model().attributeExists("policies"));

        verify(pointPolicyService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET /admin/pointPolicies/create - 포인트 정책 생성 폼 표시")
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/admin/pointPolicies/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/point/policy/create"))
                .andExpect(model().attributeExists("policy"))
                .andExpect(model().attributeExists("earningMethods"))
                .andExpect(model().attributeExists("earningTypes"));
    }

    @Test
    @DisplayName("POST /admin/pointPolicies - 포인트 정책 생성")
    void testCreatePolicy() throws Exception {
        // Given
        PointPolicyRequestDto requestDto = PointPolicyRequestDto.builder()
                .earningMethod(PointPolicy.EarningMethod.REVIEW)
                .earningValue(BigDecimal.valueOf(100))
                .description("Review Points")
                .available(true)
                .earningType(PointPolicy.EarningType.FIX)
                .build();

        mockMvc.perform(post("/admin/pointPolicies")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("earningMethod", requestDto.getEarningMethod().name())
                        .param("earningValue", requestDto.getEarningValue().toString())
                        .param("description", requestDto.getDescription())
                        .param("available", String.valueOf(requestDto.isAvailable()))
                        .param("earningType", requestDto.getEarningType().name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pointPolicies"));

        verify(pointPolicyService, times(1)).create(any(PointPolicyRequestDto.class));
    }

    @Test
    @DisplayName("GET /admin/pointPolicies/edit/{id} - 포인트 정책 수정 폼")
    void testShowEditForm() throws Exception {
        // Given
        PointPolicyResponseDto responseDto = PointPolicyResponseDto.builder()
                .pointPolicyId(1L)
                .earningMethod(PointPolicy.EarningMethod.REVIEW)
                .earningValue(BigDecimal.valueOf(100))
                .description("Review Points")
                .available(true)
                .earningType(PointPolicy.EarningType.FIX)
                .build();

        when(pointPolicyService.getById(1L)).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/admin/pointPolicies/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/point/policy/edit"))
                .andExpect(model().attributeExists("policy"))
                .andExpect(model().attributeExists("earningMethods"))
                .andExpect(model().attributeExists("earningTypes"));

        verify(pointPolicyService, times(1)).getById(1L);
    }

    @Test
    @DisplayName("POST /admin/pointPolicies/update/{id} - 포인트 정책 수정")
    void testUpdatePolicy() throws Exception {
        mockMvc.perform(post("/admin/pointPolicies/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("earningMethod", "REVIEW")
                        .param("earningValue", "100")
                        .param("description", "Updated Review Points")
                        .param("available", "true")
                        .param("earningType", "FIX"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pointPolicies"));

        verify(pointPolicyService, times(1)).update(eq(1L), any(PointPolicyRequestDto.class));
    }

    @Test
    @DisplayName("POST /admin/pointPolicies/delete/{id} - 포인트 정책 삭제")
    void testDeletePolicy() throws Exception {
        mockMvc.perform(post("/admin/pointPolicies/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pointPolicies"));

        verify(pointPolicyService, times(1)).delete(1L);
    }
}
