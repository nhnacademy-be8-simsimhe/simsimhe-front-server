package com.simsimbookstore.frontserver.cart.client;


import com.simsimbookstore.frontserver.cart.dto.CartRequestDto;
import com.simsimbookstore.frontserver.cart.dto.CartResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart-api-server", url = "http://localhost:8000/api/shop/cart")
public interface CartClient {

    @GetMapping("/book/{bookId}")
    CartResponseDto getBookForCart(@PathVariable(name = "bookId") String bookId,
                                   @RequestParam(name = "quantity") int quantity);

    @PutMapping("/migrate/user/{userId}")
    void CartToDB(@PathVariable(name = "userId") Long userId,
                  @RequestBody List<CartRequestDto> requestDtoList);
}
