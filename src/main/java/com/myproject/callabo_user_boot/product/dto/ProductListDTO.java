package com.myproject.callabo_user_boot.product.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductListDTO {

    private Long productNo;

    private String productName;

    private Integer productPrice;

    private String productImageUrl;

    private String productStatus;

    private String creatorId;
}
