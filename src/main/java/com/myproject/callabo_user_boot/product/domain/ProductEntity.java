package com.myproject.callabo_user_boot.product.domain;

import com.myproject.callabo_user_boot.category.domain.CategoryEntity;
import com.myproject.callabo_user_boot.common.BasicEntity;
import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    private Long productNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creatorEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no", referencedColumnName = "category_no")
    private CategoryEntity categoryEntity;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description", columnDefinition = "TEXT")
    private String productDescription;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "product_status")
    private String productStatus;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> productImages = new ArrayList<>();

    public void addProductImage(ProductImageEntity productImage) {
        if (productImages == null) {
            productImages = new ArrayList<>();
        }
        productImages.add(productImage);
        productImage.linkToProduct(this);
    }
}
