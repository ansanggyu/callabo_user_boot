package com.myproject.callabo_user_boot.qna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class
QnAReadDTO {

    private String question;

    private LocalDateTime createdAt;

    private String answer;

    private String customerId;

    private List<QnAImageDTO> qnaImages;

    @JsonProperty("createdAt")
    public String getFormattedCreatedAt() {
        if (createdAt != null) {
            return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }
}
