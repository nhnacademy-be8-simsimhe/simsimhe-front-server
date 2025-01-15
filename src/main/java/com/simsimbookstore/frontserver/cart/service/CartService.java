package com.simsimbookstore.frontserver.cart.service;

import com.simsimbookstore.frontserver.books.book.dto.BookRequestDto;
import com.simsimbookstore.frontserver.cart.client.CartClient;
import com.simsimbookstore.frontserver.cart.dto.CartRequestDto;
import com.simsimbookstore.frontserver.cart.dto.CartResponseDto;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartClient cartClient;
    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * 도서 장바구니에 담는 메서드
     */
//    public CartResponseDto addBookInCart(String userId, CartRequestDto cartRequestDto) {
//
//        //이미 장바구니에 있는 책이면 객체를 가져옵니다.
//        CartResponseDto cart = (CartResponseDto) redisTemplate.opsForHash().get(userId, cartRequestDto.getBookId());
//
//        //객체가 있으면 도서의 수량과 재고만 수정합니다.
//        if (cart != null) {
//            CartResponseDto updateCart = cartClient.getBookForCart(cartRequestDto.getBookId(), cartRequestDto.getQuantity());
//            cart.setQuantity(cart.getQuantity() + cartRequestDto.getQuantity());
//            redisTemplate.opsForHash().put(userId, cart.getBookId().toString(), cart);
//            return updateCart;
//        } else {
//            //객체가 없으면 해시 Key(책아이디) value(장바구니 객체) 값 추가
//            CartResponseDto newCart = cartClient.getBookForCart(cartRequestDto.getBookId(), cartRequestDto.getQuantity()); //장바구니에 담으려는 도서 정보가져오기
//            newCart.setUserId(userId);
//            redisTemplate.opsForHash().put(userId, newCart.getBookId().toString(), newCart);
//            return newCart;
//        }
//    }
    public CartResponseDto addBookInCart(String customerId, CartRequestDto cartRequestDto) {
        // Redis에서 기존 장바구니 데이터 가져오기
        CartResponseDto cart = (CartResponseDto) redisTemplate.opsForHash().get(customerId, cartRequestDto.getBookId());

        if (cart != null) {
            // 기존 장바구니 데이터가 있으면 수량 업데이트
            CartResponseDto updateCart = cartClient.getBookForCart(cartRequestDto.getBookId(), cartRequestDto.getQuantity());
            cart.setQuantity(cart.getQuantity() + cartRequestDto.getQuantity());
            redisTemplate.opsForHash().put(customerId, cart.getBookId().toString(), cart);
        } else {
            // 새로운 장바구니 항목 추가
            CartResponseDto newCart = cartClient.getBookForCart(cartRequestDto.getBookId(), cartRequestDto.getQuantity());
            newCart.setUserId(customerId);
            redisTemplate.opsForHash().put(customerId, newCart.getBookId().toString(), newCart);
        }

        // TTL 갱신
        redisTemplate.expire(customerId, 7, TimeUnit.DAYS);

        return cart;
    }


    /**
     * 비회원의 UUID 기반 customerId를 가져오거나 생성
     */
    private String getOrCreateGuestCustomerId(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 guestCartId 값 검색
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("guestCartId".equals(cookie.getName())) {
                    String guestId = cookie.getValue();
                    // TTL 갱신
                    redisTemplate.expire(guestId, 7, TimeUnit.DAYS);
                    return guestId;
                }
            }
        }

        // 쿠키가 없다면 새 UUID 생성 및 쿠키 저장
        String guestId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("guestCartId", guestId);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유지 7일
        response.addCookie(cookie);

        // Redis에 장바구니 초기화 및 TTL 설정
        generateCart(guestId);

        return guestId;
    }


    /**
     * 유저 아이디에 맞는 장바구니 도서 정보를 가져오는 메서드
     */
//    public List<CartResponseDto> getCartByUser(String userId) {
//        Map<Object, Object> cartList = redisTemplate.opsForHash().entries(userId);
//        List<CartResponseDto> bookListInCart = new ArrayList<>();
//
//        for (Object cart : cartList.values()) {
//            bookListInCart.add((CartResponseDto) cart);
//        }
//
//        return bookListInCart;
//    }
    public List<CartResponseDto> getCartByUser(String customerId) {
        // Redis에서 장바구니 데이터 가져오기
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Invalid customerId: customerId must not be null or empty");
        }

        Map<Object, Object> cartList = redisTemplate.opsForHash().entries(customerId);

        // 장바구니 데이터를 DTO 리스트로 변환
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
     */
//    public CartResponseDto deleteBookInCart(String userId, String bookId) {
//        CartResponseDto cart = (CartResponseDto) redisTemplate.opsForHash().get(userId, bookId);
//        redisTemplate.opsForHash().delete(userId, bookId);
//
//        return cart;
//    }
    public CartResponseDto deleteBookInCart(String customerId, String bookId) {
        CartResponseDto cart = (CartResponseDto) redisTemplate.opsForHash().get(customerId, bookId);
        redisTemplate.opsForHash().delete(customerId, bookId);

        return cart;
    }

    /**
     * 장바구나 도서 수량 변경
     */
    public CartResponseDto updateCartQuantity(String customerId, CartRequestDto requestDto) {
        CartResponseDto cart = (CartResponseDto) redisTemplate.opsForHash().get(customerId, requestDto.getBookId());
        Objects.requireNonNull(cart).setQuantity(requestDto.getQuantity());
        redisTemplate.opsForHash().put(customerId, cart.getBookId().toString(), cart);
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

    /**
     * 회원 또는 비회원 customerId를 가져오거나 생성
     */
    public String getOrCreateCustomerId(CustomUserDetails customUserDetails,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        // 로그인한 회원일 경우 사용자의 ID가져옴
        if (customUserDetails != null && customUserDetails.getUserId() != null) {
            return customUserDetails.getUserId().toString();
        }

        // 비회원일 경우 UUID 기반 customerId 생성 또는 가져오기
        return getOrCreateGuestCustomerId(request, response);
    }

    private void generateCart(String customerId) {
        // 비회원 장바구니가 존재하지 않을 경우 초기화
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(customerId);
        if (entries.isEmpty()) {
            //비회원 장바구니 7일동안 유지
            redisTemplate.opsForHash().put(customerId, "expire", LocalDateTime.now().plusDays(7).toString());
            // Redis Key에 7일 TTL 설정
            redisTemplate.expire(customerId, 7, TimeUnit.DAYS);
        }
    }

}
