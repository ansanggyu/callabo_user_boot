package com.myproject.callabo_user_boot.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDTO {
    private Long productNo;
    private Integer quantity;
    private Integer unitPrice;
}
