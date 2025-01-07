package com.simsimbookstore.frontserver.cart.controller;


import com.simsimbookstore.frontserver.cart.dto.CartResponseDto;
import com.simsimbookstore.frontserver.cart.service.CartService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
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
     * 사용자가 담은 장바구니 조회 로그인하지 않은 사용자는 로그인 해달라고 페이지에서 보여줌 비회원 구현 못함
     * @param model
     * @param customUserDetails
     * @return
     */
    @GetMapping("/customer")
    public String getCartByUser(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails == null || customUserDetails.getUserId() == null) {
            model.addAttribute("message", "로그인 부탁드립니다.");
            return "cart/cartDetail"; // 메시지와 함께 동일한 화면으로 이동
        }
        Long userId = customUserDetails.getUserId();
        List<CartResponseDto> cartList = cartService.getCartByUser(String.valueOf(userId));

        int totalPrice = cartList.stream()
                .mapToInt(c -> c.getPrice() * c.getQuantity())
                .sum();

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("userId", String.valueOf(userId));

        return "cart/cartDetail";
    }

}
