package com.myproject.callabo_user_boot.creator.service;

import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.customer.dto.CreatorFollowDTO;
import com.myproject.callabo_user_boot.creator.dto.CreatorListDTO;
import com.myproject.callabo_user_boot.creator.repository.CreatorRepository;
import com.myproject.callabo_user_boot.customer.domain.CreatorFollowEntity;
import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CreatorService {

    private final CreatorRepository creatorRepository;
    private final EntityManager entityManager;

    public List<CreatorListDTO> getCreatorList() {
        return creatorRepository.getCreatorList();
    }

    @Transactional
    public void toggleFollowStatus(CreatorFollowDTO creatorFollowDTO) {
        // JPQL을 사용하여 현재 상태를 조회
        String jpql = """
        SELECT cf
        FROM CreatorFollowEntity cf
        WHERE cf.customerEntity.customerId = :customerId
        AND cf.creatorEntity.creatorId = :creatorId
    """;

        List<CreatorFollowEntity> existingFollows = entityManager.createQuery(jpql, CreatorFollowEntity.class)
                .setParameter("customerId", creatorFollowDTO.getCustomerId())
                .setParameter("creatorId", creatorFollowDTO.getCreatorId())
                .getResultList();

        if (!existingFollows.isEmpty()) {
            // 기존 팔로우 상태가 있으면 토글 (팔로우 -> 언팔로우, 언팔로우 -> 팔로우)
            CreatorFollowEntity followEntity = existingFollows.get(0);
            followEntity.setFollowStatus(!followEntity.getFollowStatus()); // 현재 상태를 반대로 설정
            entityManager.merge(followEntity);
        } else {
            // 팔로우 데이터가 없으면 새로 생성
            CreatorEntity creatorEntity = entityManager.find(CreatorEntity.class, creatorFollowDTO.getCreatorId());
            CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, creatorFollowDTO.getCustomerId());

            if (creatorEntity == null || customerEntity == null) {
                throw new IllegalArgumentException("해당 Creator 또는 Customer가 존재하지 않습니다.");
            }

            // 새로운 팔로우 엔티티 생성
            CreatorFollowEntity newFollowEntity = CreatorFollowEntity.builder()
                    .creatorEntity(creatorEntity)
                    .customerEntity(customerEntity)
                    .followStatus(true) // 기본적으로 팔로우 상태로 설정
                    .build();

            entityManager.persist(newFollowEntity);
        }
    }

    public boolean checkFollowStatus(String customerId, String creatorId) {
        String jpql = """
        SELECT COUNT(cf)
        FROM CreatorFollowEntity cf
        WHERE cf.customerEntity.customerId = :customerId
        AND cf.creatorEntity.creatorId = :creatorId
        AND cf.followStatus = true
    """;

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("customerId", customerId)
                .setParameter("creatorId", creatorId)
                .getSingleResult();

        return count > 0; // 팔로우 상태가 존재하면 true 반환
    }


}
