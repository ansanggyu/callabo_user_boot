package com.myproject.callabo_user_boot.customer.domain;

import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "creator_follow")
@Data
public class CreatorFollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_follow_no", nullable = false)
    private Long creatorFollowNo;

    @Column(name = "follow_status")
    private Boolean followStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creatorEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customerEntity;
}
