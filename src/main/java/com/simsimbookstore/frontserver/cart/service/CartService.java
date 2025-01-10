package com.simsimbookstore.frontserver.cart.service;

import com.simsimbookstore.frontserver.books.book.dto.BookRequestDto;
import com.simsimbookstore.frontserver.cart.client.CartClient;
import com.simsimbookstore.frontserver.cart.dto.CartRequestDto;
import com.simsimbookstore.frontserver.cart.dto.CartResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartClient cartClient;
    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * 도서 장바구니에 담는 메서드
     *
     * @param userId
     * @param cartRequestDto
     * @return
     */
    public CartResponseDto addBookInCart(String userId, CartRequestDto cartRequestDto) {

        //이미 장바구니에 있는 책이면 객체를 가져옵니다.
        CartResponseDto cart = (CartResponseDto) redisTemplate.opsForHash().get(userId, cartRequestDto.getBookId());

        //객체가 있으면 도서의 수량과 재고만 수정합니다.
        if (cart != null) {
            CartResponseDto updateCart = cartClient.getBookForCart(cartRequestDto.getBookId(), cartRequestDto.getQuantity());
            cart.setQuantity(cart.getQuantity() + cartRequestDto.getQuantity());
            redisTemplate.opsForHash().put(userId, cart.getBookId().toString(), cart);
            return updateCart;
        } else {
            //객체가 없으면 해시 Key(책아이디) value(장바구니 객체) 값 추가
            CartResponseDto newCart = cartClient.getBookForCart(cartRequestDto.getBookId(), cartRequestDto.getQuantity()); //장바구니에 담으려는 도서 정보가져오기
            newCart.setUserId(userId);
            redisTemplate.opsForHash().put(userId, newCart.getBookId().toString(), newCart);
            return newCart;
        }
    }

    /**
     * 유저 아이디에 맞는 장바구니 도서 정보를 가져오는 메서드
     *
     * @param userId
     * @return
     */
    public List<CartResponseDto> getCartByUser(String userId) {
        Map<Object, Object> cartList = redisTemplate.opsForHash().entries(userId);
        List<CartResponseDto> bookListInCart = new ArrayList<>();

        for (Object cart : cartList.values()) {
            bookListInCart.add((CartResponseDto) cart);
        }

        return bookListInCart;
    }

    /**
     * 로그아웃,토큰 만료시 DB에 장바구니테이블로 옮기기 -> 회원이 로그아웃 할때
     *
     * @param userId
     */
    public void migrateCartToDB(String userId) {
        Map<Object, Object> cartList = redisTemplate.opsForHash().entries(userId);
        List<CartResponseDto> bookListInCart = new ArrayList<>();

        for (Object cart : cartList.values()) {
            bookListInCart.add((CartResponseDto) cart);
        }


        List<CartRequestDto> requestDtoList = bookListInCart.stream()
                .map(c -> CartRequestDto.builder()
                        .bookId(String.valueOf(c.getBookId()))
                        .quantity(c.getQuantity())
                        .userId(c.getUserId())
                        .build()).collect(Collectors.toList());

        cartClient.CartToDB(Long.valueOf(userId), requestDtoList);
    }


    /**
     * 장바구니에 책을 삭제
     *
     * @param userId
     * @param bookId
     * @return
     */
    public CartResponseDto deleteBookInCart(String userId, String bookId) {
        CartResponseDto cart = (CartResponseDto) redisTemplate.opsForHash().get(userId, bookId);
        redisTemplate.opsForHash().delete(userId, bookId);

        return cart;
    }

    /**
     * 장바구나 도서 수량 변경
     *
     * @param userId
     * @param requestDto
     * @return
     */
    public CartResponseDto updateCartQuantity(String userId, CartRequestDto requestDto) {
        CartResponseDto cart = (CartResponseDto) redisTemplate.opsForHash().get(userId, requestDto.getBookId());
        Objects.requireNonNull(cart).setQuantity(requestDto.getQuantity());
        redisTemplate.opsForHash().put(userId, cart.getBookId().toString(), cart);
        return cart;
    }

    /**
     * 주문후 장바구니 비우기 -> 결제 할때
     *
     * @param userId
     */
    public void deleteCart(String userId) {
        redisTemplate.delete(userId);
    }
}
