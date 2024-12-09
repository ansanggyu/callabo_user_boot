package com.myproject.callabo_user_boot.qna.repository;

import com.myproject.callabo_user_boot.qna.domain.QnAEntity;
import com.myproject.callabo_user_boot.qna.repository.search.QnASearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnARepository extends JpaRepository<QnAEntity, Long>, QnASearch {
}
