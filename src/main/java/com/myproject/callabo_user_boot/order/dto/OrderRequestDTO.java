package com.myproject.callabo_user_boot.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private String customerId;
    private String recipientName;
    private String recipientPhone;
    private String customerAddress;
    private String customerAddrDetail;
    private String creatorId;
    private List<OrderItemRequestDTO> items;
}
