package com.simsimbookstore.frontserver.order.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.simsimbookstore.frontserver.order.dto.*;
import com.simsimbookstore.frontserver.order.client.*;
import com.simsimbookstore.frontserver.util.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderClient orderClient;

    @Mock
    private OrderHistoryClient orderHistoryClient;

    @Mock
    private OrderDetailClient orderDetailClient;

    @Mock
    private OrderRefundClient orderRefundClient;

    @InjectMocks
    private OrderService orderService;

    private BookListRequestDto mockRequestDto;
    private BookListResponseDto mockResponseDto;

    @BeforeEach
    void setUp() {
        mockRequestDto = BookListRequestDto.builder()
                .bookId(1L)
                .quantity(2)
                .build();

        mockResponseDto = BookListResponseDto.builder()
                .bookId(1L)
                .title("Sample Book")
                .price(BigDecimal.valueOf(10000))
                .quantity(2)
                .build();
    }

    @Test
    @DisplayName("doGuestOrder() - 비회원 주문")
    void testDoGuestOrder() {
        // Given
        List<BookListRequestDto> requestDtos = List.of(mockRequestDto);
        List<BookListResponseDto> responseDtos = List.of(mockResponseDto);
        when(orderClient.doGuestOrder(requestDtos)).thenReturn(responseDtos);

        // When
        List<BookListResponseDto> result = orderService.doGuestOrder(requestDtos);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockResponseDto.getBookId(), result.getFirst().getBookId());
        verify(orderClient, times(1)).doGuestOrder(requestDtos);
    }

    @Test
    @DisplayName("doMemberOrder() - 회원 주문")
    void testDoMemberOrder() {
        // Given
        Long userId = 1L;
        List<BookListRequestDto> requestDtos = List.of(mockRequestDto);
        List<BookListResponseDto> responseDtos = List.of(mockResponseDto);
        when(orderClient.doMemberOrder(userId, requestDtos)).thenReturn(responseDtos);

        // When
        List<BookListResponseDto> result = orderService.doMemberOrder(userId, requestDtos);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockResponseDto.getBookId(), result.get(0).getBookId());
        verify(orderClient, times(1)).doMemberOrder(userId, requestDtos);
    }

    @Test
    @DisplayName("calculateTotal() - 총 가격 계산")
    void testCalculateTotal() {
        // Given
        TotalRequestDto requestDto = TotalRequestDto.builder()
                .userId(1L)
                .bookList(List.of(mockRequestDto))
                .usePoint(BigDecimal.valueOf(500))
                .build();

        TotalResponseDto responseDto = TotalResponseDto.builder()
                .total(BigDecimal.valueOf(20000))
                .deliveryPrice(BigDecimal.valueOf(3000))
                .originalPrice(BigDecimal.valueOf(25000))
                .build();

        when(orderClient.calculateTotal(requestDto)).thenReturn(responseDto);

        // When
        TotalResponseDto result = orderService.calculateTotal(requestDto);

        // Then
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(20000), result.getTotal());
        verify(orderClient, times(1)).calculateTotal(requestDto);
    }

    @Test
    @DisplayName("getOrderHistory() - 주문 기록 조회")
    void testGetOrderHistory() {
        // Given
        Long userId = 1L;
        int page = 1, size = 10;
        PageResponse<OrderHistoryResponseDto> mockPageResponse = PageResponse.<OrderHistoryResponseDto>builder()
                .data(List.of())
                .currentPage(page)
                .totalPage(5)
                .totalElements(50L)
                .build();
        when(orderHistoryClient.getOrders(userId, page, size)).thenReturn(mockPageResponse);

        // When
        PageResponse<OrderHistoryResponseDto> result = orderService.getOrderHistory(userId, page, size);

        // Then
        assertNotNull(result);
        assertEquals(5, result.getTotalPage());
        verify(orderHistoryClient, times(1)).getOrders(userId, page, size);
    }

    @Test
    @DisplayName("orderDetailHistory() - 회원 주문 상세 조회")
    void testOrderDetailHistory() {
        // Given
        Long userId = 1L;
        String orderNumber = "ORD12345";
        OrderDetailResponseDto mockDetailResponse = new OrderDetailResponseDto(null, List.of());
        when(orderDetailClient.getOrderDetailHistory(userId, orderNumber)).thenReturn(ResponseEntity.ok(mockDetailResponse));

        // When
        OrderDetailResponseDto result = orderService.orderDetailHistory(userId, orderNumber);

        // Then
        assertNotNull(result);
        verify(orderDetailClient, times(1)).getOrderDetailHistory(userId, orderNumber);
    }

    @Test
    @DisplayName("guestOrderDetail() - 비회원 주문 상세 조회")
    void testGuestOrderDetail() {
        // Given
        String orderNumber = "ORD12345";
        String email = "guest@example.com";
        OrderDetailResponseDto mockDetailResponse = new OrderDetailResponseDto(null, List.of());
        when(orderDetailClient.guestOrderDetail(orderNumber, email)).thenReturn(mockDetailResponse);

        // When
        OrderDetailResponseDto result = orderService.guestOrderDetail(orderNumber, email);

        // Then
        assertNotNull(result);
        verify(orderDetailClient, times(1)).guestOrderDetail(orderNumber, email);
    }

    @Test
    @DisplayName("applyRefund() - 환불 신청")
    void testApplyRefund() {
        // Given
        String orderNumber = "ORD12345";
        Long userId = 1L;
        CancelRequestDto cancelRequestDto = new CancelRequestDto(List.of("ITEM1"), "Reason", List.of(1), LocalDateTime.now());
        when(orderRefundClient.refund(userId, orderNumber, cancelRequestDto)).thenReturn(ResponseEntity.ok().build());

        // When
        ResponseEntity<Void> response = orderService.applyRefund(orderNumber, cancelRequestDto, userId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // HttpStatus 비교
        verify(orderRefundClient, times(1)).refund(userId, orderNumber, cancelRequestDto);
    }

}
