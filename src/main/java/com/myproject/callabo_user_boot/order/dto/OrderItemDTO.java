package com.myproject.callabo_user_boot.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private Long productNo;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
}