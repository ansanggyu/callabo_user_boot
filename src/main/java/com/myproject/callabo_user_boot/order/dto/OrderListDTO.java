package com.myproject.callabo_user_boot.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderListDTO {
    private Long orderNo;               // 주문 번호
    private String orderDate;           // 주문 날짜
    private String creatorName;         // 제작자 이름
    private String customerName;        // 고객 이름
    private Integer totalAmount;        // 총 수량
    private Integer totalPrice;         // 총 금액
    private String status;              // 주문 상태 (ENUM)
    private List<OrderItemListDTO> items;   // 주문 상품 목록
}
