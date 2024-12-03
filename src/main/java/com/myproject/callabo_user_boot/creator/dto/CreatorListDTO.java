package com.myproject.callabo_user_boot.creator.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatorListDTO {

    private String creatorId;

    private String creatorName;

    private String backgroundImg;

    private String logoImg;

    private Boolean followStatus;
}
