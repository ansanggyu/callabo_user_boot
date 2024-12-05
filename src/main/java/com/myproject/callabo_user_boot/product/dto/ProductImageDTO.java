package com.myproject.callabo_user_boot.product.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductImageDTO {

    private int productImageNo;

    private String productImageUrl;

    private int productImageOrd;
}
