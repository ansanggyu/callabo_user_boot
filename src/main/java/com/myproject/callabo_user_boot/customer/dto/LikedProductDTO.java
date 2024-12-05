package com.myproject.callabo_user_boot.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikedProductDTO {
    private Long productId;
    private String productName;
    private String productImageUrl;
    private Integer productPrice;
}
