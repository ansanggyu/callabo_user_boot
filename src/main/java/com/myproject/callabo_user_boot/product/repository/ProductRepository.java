package com.myproject.callabo_user_boot.product.repository;

import com.myproject.callabo_user_boot.product.domain.ProductEntity;
import com.myproject.callabo_user_boot.product.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductSearch {

    @Query("""
                SELECT p, c, r, ri, i
                FROM ProductEntity p
                LEFT JOIN p.categoryEntity c
                LEFT JOIN ReviewEntity r ON r.productEntity.productNo = p.productNo
                LEFT JOIN ReviewImageEntity ri ON ri.reviewEntity.reviewNo = r.reviewNo
                LEFT JOIN ProductImageEntity i ON i.productEntity.productNo = p.productNo
                WHERE p.productNo = :productNo
            """)
    List<Object[]> readProductDetails(@Param("productNo") Long productNo);
}

