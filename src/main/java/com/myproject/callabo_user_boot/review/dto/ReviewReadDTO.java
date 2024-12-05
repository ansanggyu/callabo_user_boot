package com.myproject.callabo_user_boot.review.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewReadDTO {

    private Long reviewNo;

    // 리뷰 평점
    private int rating;

    // 리뷰 내용
    private String comment;

    // 리뷰 등록일
    private LocalDateTime createdAt;

    // 리뷰 이미지 리스트
    private List<ReviewImageDTO> reviewImages;
}
