package com.myproject.callabo_user_boot.order.repository;

import com.myproject.callabo_user_boot.order.domain.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
}
