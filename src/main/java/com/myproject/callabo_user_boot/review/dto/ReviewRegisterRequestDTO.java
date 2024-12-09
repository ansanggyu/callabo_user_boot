package com.myproject.callabo_user_boot.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewRegisterRequestDTO {
    private Integer rating;                 // 평점
    private String comment;                // 리뷰 내용
    private Long productNo;                // 상품 번호
    private String customerId;             // 고객 ID
    private List<String> reviewImageUrls;  // 첨부 이미지 URL
}
