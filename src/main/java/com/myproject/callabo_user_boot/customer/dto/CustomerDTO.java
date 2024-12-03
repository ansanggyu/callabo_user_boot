package com.myproject.callabo_user_boot.customer.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomerDTO {

    private String customerId;

    private String customerName;

    private String customerProfileImage;

    public CustomerDTO() {

    }
}
