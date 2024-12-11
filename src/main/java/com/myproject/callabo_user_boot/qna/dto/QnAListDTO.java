package com.myproject.callabo_user_boot.qna.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QnAListDTO {

    private Long qnaNo;

    private String question;

    private String customerId;

    private LocalDateTime createdAt;
}
