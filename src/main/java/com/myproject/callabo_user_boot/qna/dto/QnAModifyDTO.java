package com.myproject.callabo_user_boot.qna.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QnAModifyDTO {

    private Long qnaNo;

    private String question;

    private List<QnAImageDTO> qnaImages;

    private LocalDateTime updatedAt;
}
