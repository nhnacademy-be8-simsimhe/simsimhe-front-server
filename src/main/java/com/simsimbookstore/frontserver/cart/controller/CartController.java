package com.simsimbookstore.frontserver.cart.controller;


import com.simsimbookstore.frontserver.cart.dto.CartResponseDto;
import com.simsimbookstore.frontserver.cart.service.CartService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    /**
     * 사용자가 담은 장바구니 조회
     *
     * @param model
     * @param customUserDetails
     * @return
     */
//    @GetMapping("/customer")
//    public String getCartByUser(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
//
//        if (customUserDetails == null || customUserDetails.getUserId() == null) {
//            model.addAttribute("message", "로그인 부탁드립니다.");
//            return "cart/cartDetail"; // 메시지와 함께 동일한 화면으로 이동
//        }
//        Long userId = customUserDetails.getUserId();
//        List<CartResponseDto> cartList = cartService.getCartByUser(String.valueOf(userId));
//
//        int totalPrice = cartList.stream()
//                .mapToInt(c -> c.getPrice() * c.getQuantity())
//                .sum();
//
//        model.addAttribute("cartList", cartList);
//        model.addAttribute("totalPrice", totalPrice);
//        model.addAttribute("userId", String.valueOf(userId));
//
//        return "cart/cartDetail";
//    }
    @GetMapping("/customer")
    public String getCartByUser(Model model,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        // 회원 또는 비회원 customerId 가져오기
        String customerId = cartService.getOrCreateCustomerId(customUserDetails, request, response);

        // 장바구니 데이터 가져오기
        List<CartResponseDto> cartList = cartService.getCartByUser(customerId);

        // 총 가격 계산
        int totalPrice = cartList.stream()
                .mapToInt(c -> c.getPrice() * c.getQuantity())
                .sum();

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("userId", customerId);

        return "cart/cartDetail";
    }


}
