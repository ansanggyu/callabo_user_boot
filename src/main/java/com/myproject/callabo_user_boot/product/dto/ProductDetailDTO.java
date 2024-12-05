package com.myproject.callabo_user_boot.product.dto;

import com.myproject.callabo_user_boot.review.dto.ReviewReadDTO;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDetailDTO {
    // 상품
    private Long productNo;

    private String productName;

    private String productPrice;

    private String productDescription;

    private List<ProductImageDTO> productImages;

    // 카테고리
    private String categoryName;

    // 리뷰 리스트
    private List<ReviewReadDTO> reviews;
}
