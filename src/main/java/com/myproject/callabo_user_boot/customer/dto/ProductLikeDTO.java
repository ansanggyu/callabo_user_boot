package com.myproject.callabo_user_boot.customer.dto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductLikeDTO {
    private String customerId;

    private String productId;

    private Boolean likeStatus;
}
