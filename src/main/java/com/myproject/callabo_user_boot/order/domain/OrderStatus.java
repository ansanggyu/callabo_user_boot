package com.myproject.callabo_user_boot.order.domain;

public enum OrderStatus {
    PENDING,    // 주문 생성됨, 결제 대기
    COMPLETED,  // 결제 완료
    CANCELED    // 주문 취소 or 결제 실패
}
