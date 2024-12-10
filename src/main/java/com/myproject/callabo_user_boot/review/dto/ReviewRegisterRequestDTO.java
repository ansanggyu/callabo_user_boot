package com.myproject.callabo_user_boot.review.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRegisterRequestDTO {
    private Integer rating;                 // 평점
    private String comment;                // 리뷰 내용
    private Long productNo;                // 상품 번호
    private String customerId;             // 고객 ID
    private List<ReviewImageDTO> reviewImageUrls;  // 첨부 이미지 URL
}
