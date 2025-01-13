package com.simsimbookstore.frontserver.order.controller;


import com.simsimbookstore.frontserver.order.dto.BookListRequestDto;
import com.simsimbookstore.frontserver.order.dto.BookListResponseDto;
import com.simsimbookstore.frontserver.order.dto.TotalRequestDto;
import com.simsimbookstore.frontserver.order.dto.TotalResponseDto;
import com.simsimbookstore.frontserver.order.service.OrderService;
import com.simsimbookstore.frontserver.point.service.PointHistoryService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.address.service.AddressService;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import com.simsimbookstore.frontserver.wrap.service.WrapService;
import java.util.ArrayList;
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
    private final AddressService addressService;

    @GetMapping("/shop/order")
    public String showOrderPage(
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        return "order/order_page";
    }

    @PostMapping("/shop/order")
    public String createOrder(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam("bookId") List<Long> bookIdList,
            @RequestParam("quantity") List<Integer> quantityList,
            RedirectAttributes redirectAttributes
    ) {
        List<BookListRequestDto> dtos = new ArrayList<>();
        for (int i = 0; i < bookIdList.size(); i++) {
            dtos.add(new BookListRequestDto(bookIdList.get(i), quantityList.get(i)));
        }

        // 로그인되어 있다면 userId가 존재, 아니면 null
        Long userId = (customUserDetails != null) ? customUserDetails.getUserId() : null;

        List<BookListResponseDto> responseDtos;
        if (userId != null) {
            responseDtos = orderService.doMemberOrder(userId, dtos);

            // 회원 전용 추가 정보
            redirectAttributes.addFlashAttribute("userId", userId);
            redirectAttributes.addFlashAttribute("availablePoints", pointHistoryService.getPoints(userId));
            redirectAttributes.addFlashAttribute("addresses", addressService.getAddress(userId));
        } else {
            responseDtos = orderService.doGuestOrder(dtos);
        }
        redirectAttributes.addFlashAttribute("bookOrderList", responseDtos);
        redirectAttributes.addFlashAttribute("wrapTypes", wrapService.getAllAvailableWrapTypes());

        // 주문 페이지 새로고침 & 데이터 재조회용
        return "redirect:/shop/order";
    }


    @PostMapping("/shop/order/total")
    @ResponseBody
    public ResponseEntity<?> calculateTotal(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestBody TotalRequestDto dto) {
        if (customUserDetails != null) {
            dto.setUserId(customUserDetails.getUserId());
        } else {
            dto.setUserId(null);
        }
        TotalResponseDto total = orderService.calculateTotal(dto);
        return ResponseEntity.ok(total);
    }

}
