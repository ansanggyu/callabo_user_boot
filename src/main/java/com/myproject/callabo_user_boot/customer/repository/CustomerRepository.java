package com.myproject.callabo_user_boot.customer.repository;

import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    Optional<CustomerEntity> findByCustomerId(String customerId);
}
