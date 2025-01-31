package com.simsimbookstore.frontserver.cart.controller;


import com.simsimbookstore.frontserver.cart.dto.CartRequestDto;
import com.simsimbookstore.frontserver.cart.dto.CartResponseDto;
import com.simsimbookstore.frontserver.cart.service.CartService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartRestController {


    private final CartService cartService;


    /**
     * 레디스에  장바구니 객체를 추가하는 컨트롤러
     *
     * @param requestDto
     * @param customUserDetails
     * @return
     */
    @PostMapping
    public ResponseEntity<?> addBookInCart(@RequestBody CartRequestDto requestDto,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        // 회원 또는 비회원 customerId 가져오기
        String customerId = cartService.getOrCreateCustomerId(customUserDetails, request, response);

        // 장바구니에 도서 추가
        CartResponseDto cartResponseDto = cartService.addBookInCart(customerId, requestDto);

        return ResponseEntity.ok(cartResponseDto);
    }


    /**
     * 장바구니에서 도서삭제
     *
     * @param bookId
     * @param customUserDetails
     * @return
     */
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<?> deleteBookInCart(@PathVariable(name = "bookId") String bookId,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        // 회원 또는 비회원 customerId 가져오기
        String customerId = cartService.getOrCreateCustomerId(customUserDetails, request, response);

        CartResponseDto cartResponseDto = cartService.deleteBookInCart(customerId, bookId);

        return ResponseEntity.ok(cartResponseDto);
    }

    /**
     * 책 수량 변경
     *
     * @param requestDto
     * @param customUserDetails
     * @return
     */
    @PatchMapping
    public ResponseEntity<?> updateCartQuantity(@RequestBody CartRequestDto requestDto,
                                                @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        // 회원 또는 비회원 customerId 가져오기
        String customerId = cartService.getOrCreateCustomerId(customUserDetails, request, response);

        CartResponseDto cartResponseDto = cartService.updateCartQuantity(customerId, requestDto);

        return ResponseEntity.ok(cartResponseDto);
    }


}
