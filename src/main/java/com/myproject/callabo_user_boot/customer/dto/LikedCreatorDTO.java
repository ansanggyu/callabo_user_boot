package com.myproject.callabo_user_boot.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikedCreatorDTO {
    private String creatorId;
    private String profileImg;
    private String name;
    private int likes;
}
