package com.myproject.callabo_user_boot.qna.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QnAImageDTO {

    private String qnaImageUrl;

    private Integer qnaImageOrd;
}
