package com.myproject.callabo_user_boot.order.repository;

import com.myproject.callabo_user_boot.order.domain.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}