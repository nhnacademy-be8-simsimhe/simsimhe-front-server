package com.simsimbookstore.frontserver.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailProduct {
    private String bookTitle;  // ㅇㅇ 외 1건 > book_id로 book 테이블의 title 가져오기
    private int quantity;
    private BigDecimal originalPrice;  // 판매 금액
    private BigDecimal discountPrice; // 할인된 금액
    private String packageName;  // 포장지 이름
    private BigDecimal packagePrice; // 포장지 가격
    private String couponName; // 쿠폰 이름
    private BigDecimal couponPrice;  // 할인 가격 (쿠폰)
    private OrderBookState orderBookState;  // 주문 도서 상태
}
