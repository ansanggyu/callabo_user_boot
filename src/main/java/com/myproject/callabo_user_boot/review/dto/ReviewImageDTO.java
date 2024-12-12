package com.myproject.callabo_user_boot.review.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewImageDTO {

    private int reviewImageNo;

    // 이미지 URL
    private String reviewImageUrl;

    // 이미지 순서
    private Integer reviewImageOrd;
}
