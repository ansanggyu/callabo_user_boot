package com.myproject.callabo_user_boot.product.domain;

import com.myproject.callabo_user_boot.common.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

@Table(name = "product_image")
public class ProductImageEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_no")
    private int productImageNo; // 상품 번호

    @Column(name = "product_image_url")
    private String productImageUrl; // 상품 이미지

    @Column(name = "product_image_ord")
    private int productImageOrd; // 상품 순서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productEntity;
}