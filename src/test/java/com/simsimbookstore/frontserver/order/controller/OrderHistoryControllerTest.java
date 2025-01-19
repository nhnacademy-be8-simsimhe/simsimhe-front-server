package com.simsimbookstore.frontserver.order.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import com.simsimbookstore.frontserver.order.service.OrderService;
import com.simsimbookstore.frontserver.security.provider.TokenProvider;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.UserStatus;
import com.simsimbookstore.frontserver.util.PageResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

@WebMvcTest(OrderHistoryController.class)
@Import({OrderHistoryControllerTest.MockConfig.class})
class OrderHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

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
        public OrderService orderService() {
            return mock(OrderService.class);
        }

        @Bean
        public TokenProvider tokenProvider() {
            return mock(TokenProvider.class); // 필요 시 Mock 구현
        }
    }

    @Test
    @Disabled
    @DisplayName("GET /shop/users/orders - 사용자 주문 내역 페이지 렌더링")
    void testViewOrderHistoryPage() throws Exception {
        // Given
        Long userId = 1L;
        int page = 1, size = 15;

        PageResponse<OrderHistoryResponseDto> mockPageResponse = PageResponse.<OrderHistoryResponseDto>builder()
                .data(List.of(
                        OrderHistoryResponseDto.builder()
                                .orderNumber("ORD001")
                                .orderDate(LocalDateTime.of(2025, 1, 1, 19, 0))
                                .orderName("Sample Order")
                                .orderAmount(BigDecimal.valueOf(49500.00))
                                .orderState(OrderHistoryResponseDto.OrderState.COMPLETED)
                                .trackingNumber(null)
                                .orderUserName("John Doe")
                                .receiverName("Jane Doe")
                                .build()
                ))
                .currentPage(1)
                .totalPage(3)
                .totalElements(46L)
                .build();

        when(orderService.getOrderHistory(userId, page, size)).thenReturn(mockPageResponse);

        // Mock 사용자 인증 정보 설정
        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .principalName("john.doe@example.com")
                .userId(userId)
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")))
                .userStatus(UserStatus.ACTIVE)
                .latestLoginDate(LocalDateTime.now())
                .isSocial(false)
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
        );

        // When & Then
        mockMvc.perform(get("/shop/users/orders")
                        .flashAttr("customUserDetails", customUserDetails)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(view().name("order/history/order_history"))
                .andExpect(model().attributeExists("orderHistories"))
                .andExpect(model().attribute("orderHistories", mockPageResponse))
                .andExpect(model().attributeExists("userId"))
                .andExpect(model().attribute("userId", userId));

        verify(orderService, times(1)).getOrderHistory(userId, page, size);
    }

    @Test
    @DisplayName("GET /shop/users/orders - 비회원 리다이렉트")
    void testRedirectForUnauthenticatedUser() throws Exception {
        // When & Then
        mockMvc.perform(get("/shop/users/orders"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
