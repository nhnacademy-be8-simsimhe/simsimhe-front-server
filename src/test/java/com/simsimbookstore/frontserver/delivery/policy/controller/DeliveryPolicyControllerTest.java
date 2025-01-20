package com.simsimbookstore.frontserver.delivery.policy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicy;
import com.simsimbookstore.frontserver.delivery.policy.dto.DeliveryPolicyRequestDto;
import com.simsimbookstore.frontserver.delivery.policy.service.DeliveryPolicyService;
//import com.simsimbookstore.frontserver.security.provider.TokenProvider;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DeliveryPolicyController.class)
@ExtendWith(MockitoExtension.class)
class DeliveryPolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeliveryPolicyService deliveryPolicyService;

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
        public DeliveryPolicyService deliveryPolicyService() {
            return mock(DeliveryPolicyService.class);
        }
//        @Bean
//        public TokenProvider tokenProvider() {
//            return mock(TokenProvider.class); // 필요 시 Mock 구현
//        }
    }

    @Test
    @DisplayName("GET /admin/delivery-policies - Fetch all delivery policies")
    void testGetAllDeliveryPolicies() throws Exception {
        // Given
        List<DeliveryPolicy> mockPolicies = Arrays.asList(
                new DeliveryPolicy(1L, "Standard Delivery", BigDecimal.valueOf(5000), BigDecimal.valueOf(20000), true),
                new DeliveryPolicy(2L, "Express Delivery", BigDecimal.valueOf(10000), BigDecimal.valueOf(50000), false)
        );
        when(deliveryPolicyService.findAll()).thenReturn(mockPolicies);

        // When & Then
        mockMvc.perform(get("/admin/delivery-policies"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("deliveryPolicies"))
                .andExpect(view().name("admin/delivery/deliveryPolicyList"))
                .andExpect(model().attribute("deliveryPolicies", mockPolicies));
    }

    @Test
    @DisplayName("GET /admin/delivery-policies/create - Delivery policy creation form")
    void testCreateDeliveryPolicyForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/admin/delivery-policies/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("deliveryPolicy"))
                .andExpect(view().name("admin/delivery/deliveryPolicyCreate"));
    }

    @Test
    @DisplayName("POST /admin/delivery-policies/create - Successfully create a delivery policy")
    void testCreateDeliveryPolicy() throws Exception {
        // Given
        DeliveryPolicyRequestDto requestDto = new DeliveryPolicyRequestDto(
                "Same Day Delivery", BigDecimal.valueOf(50000), true, BigDecimal.valueOf(15000)
        );

        // When & Then
        mockMvc.perform(post("/admin/delivery-policies/create")
                        .flashAttr("deliveryPolicy", requestDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/delivery-policies"));

        verify(deliveryPolicyService, times(1)).createDeliveryPolicy(any(DeliveryPolicyRequestDto.class));
    }

    @Test
    @DisplayName("POST /admin/delivery-policies/toggle/{id} - Toggle delivery policy activation")
    void testToggleDeliveryPolicy() throws Exception {
        // Given
        Long policyId = 1L;

        // When & Then
        mockMvc.perform(post("/admin/delivery-policies/toggle/{id}", policyId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/delivery-policies"));

        verify(deliveryPolicyService, times(1)).toggleDeliveryPolicy(policyId);
    }

    @Test
    @DisplayName("POST /admin/delivery-policies/delete/{id} - Delete a delivery policy")
    void testDeleteDeliveryPolicy() throws Exception {
        // Given
        Long policyId = 1L;

        // When & Then
        mockMvc.perform(post("/admin/delivery-policies/delete/{id}", policyId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/delivery-policies"));

        verify(deliveryPolicyService, times(1)).deleteDeliveryPolicy(policyId);
    }
}

