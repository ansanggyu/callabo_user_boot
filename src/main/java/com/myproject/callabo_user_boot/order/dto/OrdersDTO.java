package com.myproject.callabo_user_boot.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersDTO {
    private Long orderNo;
    private String recipientName;
    private String recipientPhone;
    private String customerAddress;
    private String customerAddrDetail;
    private Integer totalAmount;  // 총 수량
    private Integer totalPrice;   // 총 가격
    private List<OrderItemDTO> items;
}