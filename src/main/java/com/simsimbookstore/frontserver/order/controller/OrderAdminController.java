package com.simsimbookstore.frontserver.order.controller;

import com.simsimbookstore.frontserver.order.dto.OrderBookStateUpdateRequestDto;
import com.simsimbookstore.frontserver.order.dto.OrderHistoryResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderOrderBookResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderResponseDto;
import com.simsimbookstore.frontserver.order.dto.OrderStateUpdateDto;
import com.simsimbookstore.frontserver.order.service.OrderAdminService;
import com.simsimbookstore.frontserver.util.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Slf4j
@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController {

    private final OrderAdminService orderAdminService;

    /**
     * 1) 전체 주문 목록 조회 + 페이지네이션
     */
    @GetMapping
    public String getAllOrders(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "15") int size,
                               Model model) {
        // 1-1. Service 통해 주문 목록 조회
        PageResponse<OrderResponseDto> pageResponse = orderAdminService.getOrderBooks(page, size);

        // 1-2. 결과를 모델에 담아서 뷰로 전달
        model.addAttribute("pageResponse", pageResponse);
        return "admin/order/orderList";  // Thymeleaf 템플릿 (아래에서 정의)
    }

    /**
     * 2) 주문 상세 조회 (주문 정보 + 주문 도서 목록)
     */
    @GetMapping("/{orderId}")
    public String getOrderDetail(@PathVariable("orderId") Long orderId,
                                 Model model) {
        // 2-1. 주문 + 주문 도서를 한 번에 조회
        OrderOrderBookResponseDto detail = orderAdminService.getOrderOrderBook(orderId);

        // 2-2. 뷰에서 사용할 데이터 설정
        model.addAttribute("detail", detail);

        return "admin/order/orderDetail"; // Thymeleaf 템플릿 (아래에서 정의)
    }

    /**
     * 3) 주문 상태 업데이트 (POST Form submit)
     *    - 예) "PENDING", "COMPLETED", etc.
     */
    @PostMapping("/{orderId}/update-state")
    public String updateOrderState(@PathVariable("orderId") Long orderId,
                                   @RequestParam("newOrderState") String newOrderStateStr) {

        // 3-1. DTO 생성
        OrderStateUpdateDto dto = new OrderStateUpdateDto(
                orderId,
                // OrderState enum (백엔드와 동일한 Enum 이름이어야 함)
                OrderHistoryResponseDto.OrderState.valueOf(newOrderStateStr)
        );

        // 3-2. 서비스 통해 주문 상태 업데이트
        orderAdminService.updateOrderState(orderId, dto);

        // 3-3. 업데이트 후 다시 상세 페이지로 리다이렉트
        return "redirect:/admin/orders/" + orderId;
    }

    /**
     * 4) 주문 도서(OrderBook) 상태 업데이트 (POST Form submit)
     *    - 예) "PENDING", "DELIVERY_READY", "CANCELED" 등
     */
    @PostMapping("/{orderBookId}/state")
    public String updateOrderBookState(@PathVariable("orderBookId") Long orderBookId,
                                       @RequestParam("newOrderBookState") String newOrderBookState,
                                       @RequestParam("orderId") Long orderId) {

        // 4-1. DTO 생성
        OrderBookStateUpdateRequestDto reqDto = new OrderBookStateUpdateRequestDto(
                OrderBookStateUpdateRequestDto.OrderBookState.valueOf(newOrderBookState)
        );

        // 4-2. 서비스 통해 주문도서 상태 업데이트
        orderAdminService.updateOrderBook(orderBookId, reqDto);

        // 4-3. 업데이트 후 해당 주문 상세 페이지로 리다이렉트
        return "redirect:/admin/orders/" + orderId;
    }
}


