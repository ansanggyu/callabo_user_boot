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

    public List<CreatorListDTO> getCreatorsWithFollowStatus(String customerId) {
        return creatorRepository.getCreatorsWithFollowStatus(customerId);
    }

    @Transactional
    public void toggleFollowStatus(CreatorFollowDTO creatorFollowDTO) {
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
            // 기존 팔로우 상태 업데이트
            CreatorFollowEntity followEntity = existingFollows.get(0);
            followEntity.setFollowStatus(creatorFollowDTO.getFollowStatus());
            entityManager.merge(followEntity);
        } else {
            // CreatorEntity와 CustomerEntity 조회
            CreatorEntity creatorEntity = entityManager.find(CreatorEntity.class, creatorFollowDTO.getCreatorId());
            CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, creatorFollowDTO.getCustomerId());

            if (creatorEntity == null || customerEntity == null) {
                throw new IllegalArgumentException("해당 Creator 또는 Customer가 존재하지 않습니다.");
            }

            // 새로운 CreatorFollowEntity 생성 및 저장
            CreatorFollowEntity newFollowEntity = CreatorFollowEntity.builder()
                    .creatorEntity(creatorEntity)
                    .customerEntity(customerEntity)
                    .followStatus(creatorFollowDTO.getFollowStatus())
                    .build();

            entityManager.persist(newFollowEntity);
        }
    }
}
