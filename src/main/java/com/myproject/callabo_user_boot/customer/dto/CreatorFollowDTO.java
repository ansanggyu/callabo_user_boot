package com.myproject.callabo_user_boot.customer.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatorFollowDTO {

    private String customerId;

    private String creatorId;

    private Boolean followStatus;
}
