package com.myproject.callabo_user_boot.customer.repository;

import com.myproject.callabo_user_boot.customer.domain.CreatorFollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreatorFollowRepository extends JpaRepository<CreatorFollowEntity, Long> {

}

