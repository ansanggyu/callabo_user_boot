package com.myproject.callabo_user_boot.qna.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QnAReadDTO {

    private String question;

    private LocalDateTime createdAt;

    private String answer;

    private String customerId;

    private List<QnAImageDTO> qnaImages;
}
