package com.myproject.callabo_user_boot.product.repository.search;

import com.myproject.callabo_user_boot.creator.domain.QCreatorEntity;
import com.myproject.callabo_user_boot.product.domain.ProductEntity;
import com.myproject.callabo_user_boot.product.domain.QProductEntity;
import com.myproject.callabo_user_boot.product.domain.QProductImageEntity;
import com.myproject.callabo_user_boot.product.dto.ProductListDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(ProductEntity.class);
    }

    @Override
    public List<ProductListDTO> productList(String creatorId) {

        QProductEntity product = QProductEntity.productEntity;
        QProductImageEntity image = QProductImageEntity.productImageEntity;
        QCreatorEntity creator = QCreatorEntity.creatorEntity;

        JPQLQuery<ProductListDTO> query = from(product)
                .leftJoin(image).on(image.productEntity.eq(product))
                .leftJoin(creator).on(product.creatorEntity.eq(creator))
                .select(Projections.bean(ProductListDTO.class,
                        product.productNo,
                        product.productName,
                        product.productPrice,
                        product.productStatus,
                        image.productImageUrl,
                        creator.creatorId
                ));

        if (creatorId != null) {
            query.where(creator.creatorId.eq(creatorId));
        }

        return query.fetch();
    }
}
