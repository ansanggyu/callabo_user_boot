package com.myproject.callabo_user_boot.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemListDTO {
    private Long productNo;
    private String productName;    // 상품 이름
    private String productImage;  // 상품 이미지
    private Integer unitPrice;    // 단위 가격
    private Integer quantity;     // 수량
}
