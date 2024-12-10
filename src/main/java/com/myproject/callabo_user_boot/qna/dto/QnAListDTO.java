package com.myproject.callabo_user_boot.qna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @JsonProperty("createdAt")
    public String getFormattedCreatedAt() {
        if (createdAt != null) {
            return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }
}
