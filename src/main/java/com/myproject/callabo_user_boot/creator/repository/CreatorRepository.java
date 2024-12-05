package com.myproject.callabo_user_boot.creator.repository;

import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.creator.repository.search.CreatorSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<CreatorEntity, String>, CreatorSearch {

}
