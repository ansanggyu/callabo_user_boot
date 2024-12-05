package com.myproject.callabo_user_boot.customer.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDTO {

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 숫자 10~11자리여야 합니다.")
    private String customerPhone;

    private String customerZipcode;

    private String customerAddr;

    private String customerAddrDetail;
}
