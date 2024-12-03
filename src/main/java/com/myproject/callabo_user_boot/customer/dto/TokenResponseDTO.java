package com.myproject.callabo_user_boot.customer.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String creatorId;
    private String accessToken;
    private String refreshToken;
    private String creatorName;
}
