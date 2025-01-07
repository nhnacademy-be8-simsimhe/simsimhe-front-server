package com.simsimbookstore.frontserver.cart.controller;


import com.simsimbookstore.frontserver.cart.dto.CartRequestDto;
import com.simsimbookstore.frontserver.cart.dto.CartResponseDto;
import com.simsimbookstore.frontserver.cart.service.CartService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Long userId = customUserDetails.getUserId();

        CartResponseDto cartResponseDto = cartService.addBookInCart(String.valueOf(userId), requestDto);

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
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Long userId = customUserDetails.getUserId();

        CartResponseDto cartResponseDto = cartService.deleteBookInCart(String.valueOf(userId), bookId);


        return ResponseEntity.ok(cartResponseDto);
    }

    /**
     * 책 수량 변경
     * @param requestDto
     * @param customUserDetails
     * @return
     */
    @PatchMapping
    public ResponseEntity<?> updateCartQuantity(@RequestBody CartRequestDto requestDto,
                                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Long userId = customUserDetails.getUserId();

        CartResponseDto cartResponseDto = cartService.updateCartQuantity(String.valueOf(userId), requestDto);

        return ResponseEntity.ok(cartResponseDto);

    }


}
