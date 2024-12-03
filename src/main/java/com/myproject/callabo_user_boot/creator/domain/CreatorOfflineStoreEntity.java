package com.myproject.callabo_user_boot.creator.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "creator_offline_store")
public class CreatorOfflineStoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no", nullable = false)
    private Long storeNo;

    @Column(name = "store_image")
    private String storeImage;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_location")
    private String storeLocation;

    @Column(name = "latitude", precision = 10, scale = 8, nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8, nullable = false)
    private BigDecimal longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id",referencedColumnName="creator_id")
    private CreatorEntity creatorEntity;
}
