package com.simsimbookstore.frontserver.order.controller;


import com.simsimbookstore.frontserver.order.dto.BookListRequestDto;
import com.simsimbookstore.frontserver.order.dto.BookListResponseDto;
import com.simsimbookstore.frontserver.order.dto.TotalRequestDto;
import com.simsimbookstore.frontserver.order.dto.TotalResponseDto;
import com.simsimbookstore.frontserver.order.service.OrderService;
import com.simsimbookstore.frontserver.point.service.PointHistoryService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import com.simsimbookstore.frontserver.wrap.service.WrapService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final WrapService wrapService;
    private final UserService userService;
    private final PointHistoryService pointHistoryService;

    @GetMapping("/shop/order")
    public String showOrderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        return "order/order_page";
    }


    @PostMapping("/shop/order")
    public String createOrder(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long bookId,
            @RequestParam int quantity,
            RedirectAttributes redirectAttributes
    ) {
        List<BookListRequestDto> dtos = List.of(new BookListRequestDto(bookId, quantity));
        List<BookListResponseDto> responseDtos = orderService.doOrder(dtos);
        Long userId = customUserDetails.getUserId();
        redirectAttributes.addFlashAttribute("bookOrderList", responseDtos);
        redirectAttributes.addFlashAttribute("wrapTypes", wrapService.getAllAvailableWrapTypes());
        redirectAttributes.addFlashAttribute("userId", userId);
        redirectAttributes.addFlashAttribute("bookOrderList", responseDtos);
        redirectAttributes.addFlashAttribute("wrapTypes", wrapService.getAllAvailableWrapTypes());
        redirectAttributes.addFlashAttribute("availablePoints", pointHistoryService.getPoints(userId));
        redirectAttributes.addFlashAttribute("addresses", null);

        return "redirect:/shop/order";
    }



    @PostMapping
    @RequestMapping("/shop/order/total")
    @ResponseBody
    public ResponseEntity<?> calculateTotal(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestBody TotalRequestDto dto) {
        dto.setUserId(customUserDetails.getUserId());
        log.info("Calculating total for user {}", customUserDetails.getUserId());
        TotalResponseDto total = orderService.calculateTotal(dto);
        return ResponseEntity.ok(total);
    }

}
