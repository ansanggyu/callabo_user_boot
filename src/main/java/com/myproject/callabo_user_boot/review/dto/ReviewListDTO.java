package com.myproject.callabo_user_boot.review.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewListDTO {
    private Long reviewNo;
    private String customerName;
    private Integer rating;
    private LocalDateTime createdAt;
    private String creatorName;
    private String productName;
    private String productDescription;
    private List<ReviewImageDTO> reviewImages;
    private String comment;
    private String reply;

    @JsonProperty("createdAt")
    public String getFormattedCreatedAt() {
        if (createdAt != null) {
            return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }
}
