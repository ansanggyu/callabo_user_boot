package com.myproject.callabo_user_boot.qna.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QnAReadDTO {

    private String question;

    private LocalDateTime createdAt;

    private String qnaImageUrl;

    private String answer;

    private String customerId;
}
