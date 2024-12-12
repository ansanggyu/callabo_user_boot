package com.myproject.callabo_user_boot.order.repository;

import com.myproject.callabo_user_boot.order.domain.OrderItemEntity;
import com.myproject.callabo_user_boot.product.dto.ProductOrderRankingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("SELECT new com.myproject.callabo_user_boot.product.dto.ProductOrderRankingDTO(" +
            "p.productNo, " +
            "p.productName, " +
            "p.creatorEntity.creatorId, " +
            "(SELECT pi.productImageUrl FROM ProductImageEntity pi WHERE pi.productEntity = p AND pi.productImageOrd = 0), " +
            "p.productDescription, " +
            "p.productPrice, " +
            "SUM(o.quantity)) " +
            "FROM OrderItemEntity o " +
            "JOIN o.productEntity p " +
            "GROUP BY p.productNo, p.productName, p.creatorEntity.creatorId, " +
            "p.productDescription, p.productPrice " +
            "ORDER BY SUM(o.quantity) DESC")
    Page<ProductOrderRankingDTO> findTop10ByOrderQuantity(Pageable pageable);

}