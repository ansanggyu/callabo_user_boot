package com.myproject.callabo_user_boot.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderRankingDTO {
    private Long productNo;
    private String productName;
    private String creatorId;
    private String productImageUrl;
    private String productDescription;
    private Integer productPrice;
    private Long orderCount;
}