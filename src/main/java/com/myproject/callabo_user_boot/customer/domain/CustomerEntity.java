package com.myproject.callabo_user_boot.customer.domain;

import com.myproject.callabo_user_boot.common.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerEntity extends BasicEntity {

    @Id
    @Column(name="customer_id", nullable = false)
    private String customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_zipcode")
    private String customerZipcode;

    @Column(name = "customer_addr")
    private String customerAddr;

    @Column(name = "customer_addr_detail")
    private String customerAddrDetail;

    @Column(name = "customer_profile_image", nullable = false, length = 2000)
    private String customerProfileImage;
}
