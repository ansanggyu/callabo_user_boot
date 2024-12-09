package com.myproject.callabo_user_boot.qna.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QnARegisterDTO {

    private String question;

    private Long productNo;

    private String creatorId;

    private String customerId;

    private List<String> qnaImages;
}
