package com.myproject.callabo_user_boot.order.repository;

import com.myproject.callabo_user_boot.order.domain.OrdersEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
    List<OrdersEntity> findByCustomerEntity_CustomerId(String customerId, Sort sort);
}