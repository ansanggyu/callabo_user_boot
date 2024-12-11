package com.myproject.callabo_user_boot.customer.service;

import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import com.myproject.callabo_user_boot.customer.domain.ProductLikeEntity;
import com.myproject.callabo_user_boot.customer.dto.*;
import com.myproject.callabo_user_boot.customer.repository.CustomerRepository;
import com.myproject.callabo_user_boot.product.domain.ProductEntity;
import com.myproject.callabo_user_boot.product.domain.ProductImageEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    private final EntityManager entityManager;

    // 사용자 정보
    public KakaoLoginDTO authKakao(String accessToken) {

        log.info("accessToken: " + accessToken);

        // 사용자 정보 가져오기
        String email = getKakaoAccountInfo(accessToken, "email");
        log.info("email: " + email);

        String nickname = getKakaoAccountInfo(accessToken, "nickname");
        log.info("nickname: " + nickname);

        String profileImage = getKakaoAccountInfo(accessToken, "profile_image");
        log.info("profile_image: " + profileImage);

        // 사용자 정보가 없으면 예외 처리
        if (email.isEmpty()) {
            throw new RuntimeException("Failed to retrieve email from Kakao API");
        }

        // DB에서 사용자 검색
        Optional<CustomerEntity> result = customerRepository.findByCustomerId(email);
        CustomerEntity customer;

        if (result.isPresent()) {
            // 기존 사용자 업데이트
            customer = result.get();
            customer.setCustomerName(nickname);
            customer.setCustomerProfileImage(profileImage);
        } else {
            // 신규 사용자 생성
            customer = new CustomerEntity();
            customer.setCustomerId(email);
            customer.setCustomerName(nickname);
            customer.setCustomerProfileImage(profileImage);
            customer.setCustomerPhone("");
            customer.setCustomerZipcode("");
            customer.setCustomerAddr("");
            customer.setCustomerAddrDetail("");
        }

        KakaoLoginDTO dto = new KakaoLoginDTO();

        dto.setCustomerId(customer.getCustomerId());
        dto.setCustomerName(customer.getCustomerName());
        dto.setCustomerProfileImage(customer.getCustomerProfileImage());

        // 저장
        customerRepository.save(customer);

        return dto;
    }

    // 토큰
    private String getKakaoAccountInfo(String accessToken, String field) {
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    kakaoGetUserURL, HttpMethod.GET, entity, Map.class
            );

            // 응답 본문 가져오기
            Map<String, Object> body = response.getBody();
            if (body == null) {
                throw new RuntimeException("Empty response from Kakao API");
            }

            // kakao_account 가져오기
            Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
            Map<String, String> properties = (Map<String, String>) body.get("properties");

//            log.info("Kakao API Response Body: {}", body);

            // 필드별 데이터 처리
            switch (field) {
                case "email":
                    return kakaoAccount != null ? kakaoAccount.getOrDefault("email", "").toString() : "";
                case "nickname":
                    return properties != null ? properties.getOrDefault("nickname", "") : "";
                case "profile_image":
                    return properties != null ? properties.getOrDefault("profile_image", "") : "";
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
        } catch (Exception e) {
            log.error("Failed to fetch user info from Kakao API", e);
            throw new RuntimeException("Failed to fetch user info from Kakao API: " + e.getMessage(), e);
        }
    }

    public void updateCustomer(String customerId, CustomerDTO customerDTO) {
        // customerId로 기존 엔티티 조회
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 고객을 찾을 수 없습니다."));

        // DTO 데이터를 엔티티에 반영
        customer.setCustomerPhone(customerDTO.getCustomerPhone());
        customer.setCustomerZipcode(customerDTO.getCustomerZipcode());
        customer.setCustomerAddr(customerDTO.getCustomerAddr());
        customer.setCustomerAddrDetail(customerDTO.getCustomerAddrDetail());

        // 변경사항 저장
        customerRepository.save(customer);
    }

    public List<LikedProductDTO> getLikedProducts(String customerId) {
        String jpql = """
                SELECT pl, p, pi
                FROM ProductLikeEntity pl
                JOIN pl.productEntity p
                LEFT JOIN p.productImages pi ON pi.productImageOrd = 0
                WHERE pl.customerEntity.customerId = :customerId AND pl.likeStatus = true
                """;

        List<Object[]> result = entityManager.createQuery(jpql, Object[].class)
                .setParameter("customerId", customerId)
                .getResultList();

        // DTO 빌더를 이용해 결과 생성
        return result.stream()
                .map(row -> LikedProductDTO.builder()
                        .productId(((ProductEntity) row[1]).getProductNo())
                        .productName(((ProductEntity) row[1]).getProductName())
                        .productImageUrl(((ProductImageEntity) row[2]) != null ? ((ProductImageEntity) row[2]).getProductImageUrl() : null)
                        .productPrice(((ProductEntity) row[1]).getProductPrice())
                        .build())
                .toList();
    }

    public List<LikedCreatorDTO> getLikedCreators(String customerId) {
        String jpql = """
                SELECT cf.creatorEntity.creatorId,
                       cf.creatorEntity.logoImg,
                       cf.creatorEntity.creatorName,
                       COUNT(cf.followStatus)
                FROM CreatorFollowEntity cf
                WHERE cf.customerEntity.customerId = :customerId
                AND cf.followStatus = true
                GROUP BY cf.creatorEntity.creatorId,
                         cf.creatorEntity.logoImg,
                         cf.creatorEntity.creatorName
                """;

        List<Object[]> results = entityManager.createQuery(jpql, Object[].class)
                .setParameter("customerId", customerId)
                .getResultList();

        // DTO로 매핑
        return results.stream()
                .map(row -> LikedCreatorDTO.builder()
                        .creatorId((String) row[0])
                        .profileImg((String) row[1]) // logoImg를 profileImg에 매핑
                        .name((String) row[2])
                        .likes(((Long) row[3]).intValue()) // COUNT 결과를 정수로 변환
                        .build()
                ).toList();
    }
    public void toggleProductLike(ProductLikeDTO productLikeDTO) {
        // JPQL을 사용하여 현재 상태를 조회
        String jpql = """
        SELECT pl
        FROM ProductLikeEntity pl
        WHERE pl.customerEntity.customerId = :customerId
        AND pl.productEntity.productNo = :productId
        """;

        List<ProductLikeEntity> existingLikes = entityManager.createQuery(jpql, ProductLikeEntity.class)
                .setParameter("customerId", productLikeDTO.getCustomerId())
                .setParameter("productId", Long.parseLong(productLikeDTO.getProductId()))
                .getResultList();

        if (!existingLikes.isEmpty()) {
            // 기존 좋아요 상태가 있으면 토글 (좋아요 -> 취소, 취소 -> 좋아요)
            ProductLikeEntity likeEntity = existingLikes.get(0);
            likeEntity.setLikeStatus(!likeEntity.getLikeStatus()); // 현재 상태를 반대로 설정
            entityManager.merge(likeEntity);
        } else {
            // 좋아요 데이터가 없으면 새로 생성
            ProductEntity productEntity = entityManager.find(ProductEntity.class, Long.parseLong(productLikeDTO.getProductId()));
            CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, productLikeDTO.getCustomerId());
            if (productEntity == null || customerEntity == null) {
                throw new IllegalArgumentException("해당 Product 또는 Customer가 존재하지 않습니다.");
            }
            // 새로운 좋아요 엔티티 생성
            ProductLikeEntity newLikeEntity = new ProductLikeEntity();
            newLikeEntity.setProductEntity(productEntity);
            newLikeEntity.setCustomerEntity(customerEntity);
            newLikeEntity.setLikeStatus(true); // 기본적으로 좋아요 상태로 설정
            entityManager.persist(newLikeEntity);
        }
    }

    public boolean checkProductLikeStatus(String customerId, Long productId) {
        String jpql = """
        SELECT COUNT(pl)
        FROM ProductLikeEntity pl
        WHERE pl.customerEntity.customerId = :customerId
        AND pl.productEntity.productNo = :productId
        AND pl.likeStatus = true
    """;

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("customerId", customerId)
                .setParameter("productId", productId)
                .getSingleResult();

        return count > 0; // 좋아요 상태가 존재하면 true 반환
    }

}

