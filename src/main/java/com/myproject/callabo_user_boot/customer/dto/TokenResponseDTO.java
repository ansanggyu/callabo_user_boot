package com.myproject.callabo_user_boot.customer.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String customerId;

    private String accessToken;

    private String refreshToken;

    private String customerName;

    private String customerProfileImage;
}
