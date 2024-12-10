package com.myproject.callabo_user_boot.review.repository;

import com.myproject.callabo_user_boot.review.domain.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

}
