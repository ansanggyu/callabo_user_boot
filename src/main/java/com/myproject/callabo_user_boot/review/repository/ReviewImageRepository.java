package com.myproject.callabo_user_boot.review.repository;

import com.myproject.callabo_user_boot.review.domain.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Long> {
}