package com.myproject.callabo_user_boot.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String customerPhone;

    private String customerZipcode;

    private String customerAddr;

    private String customerAddrDetail;
}
