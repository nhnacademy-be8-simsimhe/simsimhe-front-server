package com.simsimbookstore.frontserver.order.dto;

public enum ReturnState {
    RETURN_REQUESTED,         // 반품요청
    RETURN_APPROVED,          // 반품승인
    RETURN_REJECTED,          // 반품거절
    RETURN_SHIPPED_BACK,      // 반송
    RETURN_ITEM_INSPECTING,   // 반송상품확인중
    RETURN_COMPLETED,         // 반품완료
    REFUND_COMPLETED          // 환불완료
}
