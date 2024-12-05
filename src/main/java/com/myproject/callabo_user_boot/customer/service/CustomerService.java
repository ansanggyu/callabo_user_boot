package com.myproject.callabo_user_boot.customer.service;

import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.creator.repository.CreatorRepository;
import com.myproject.callabo_user_boot.customer.domain.CreatorFollowEntity;
import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import com.myproject.callabo_user_boot.customer.dto.CreatorFollowDTO;
import com.myproject.callabo_user_boot.customer.dto.KakaoLoginDTO;
import com.myproject.callabo_user_boot.customer.repository.CreatorFollowRepository;
import com.myproject.callabo_user_boot.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CreatorFollowRepository creatorFollowRepository;

    private final CreatorRepository creatorRepository;

    private final RestTemplate restTemplate;

    // 사용자 정보
    public KakaoLoginDTO authKakao(String accessToken) {

        log.info("accessToken: " + accessToken);

        // 사용자 정보 가져오기
        String email = getKakaoAccountInfo(accessToken, "email");
        log.info("email: " + email);

        String nickname = getKakaoAccountInfo(accessToken, "nickname");
        log.info("nickname: " + nickname);

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
        } else {
            // 신규 사용자 생성
            customer = new CustomerEntity();
            customer.setCustomerId(email);
            customer.setCustomerName(nickname);
            customer.setCustomerProfileImage("");
            customer.setCustomerPhone("");
            customer.setCustomerZipcode("");
            customer.setCustomerAddr("");
            customer.setCustomerAddrDetail("");
        }

        KakaoLoginDTO dto = new KakaoLoginDTO();

        dto.setCustomerId(customer.getCustomerId());
        dto.setCustomerName(customer.getCustomerName());

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

            // 필드별 데이터 처리
            switch (field) {
                case "email":
                    return kakaoAccount != null ? kakaoAccount.getOrDefault("email", "").toString() : "";
                case "nickname":
                    return properties != null ? properties.getOrDefault("nickname", "") : "";
                case "profileImage":
                    return properties != null ? properties.getOrDefault("profile_image_url", "") : "";
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
        } catch (Exception e) {
            log.error("Failed to fetch user info from Kakao API", e);
            throw new RuntimeException("Failed to fetch user info from Kakao API: " + e.getMessage(), e);
        }
    }

    // 팔로우 상태 변경
    public CreatorFollowDTO toggleFollow(CreatorFollowDTO followDTO) {

        // 팔로우 상태 조회
        CreatorFollowEntity followEntity = creatorFollowRepository
                .findByCustomerEntity_CustomerIdAndCreatorEntity_CreatorId(
                        followDTO.getCustomerId(),
                        followDTO.getCreatorId()
                )
                .orElseGet(() -> {
                    CustomerEntity customerEntity = customerRepository.findById(followDTO.getCustomerId())
                            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
                    CreatorEntity creatorEntity = creatorRepository.findById(followDTO.getCreatorId())
                            .orElseThrow(() -> new IllegalArgumentException("Creator not found"));

                    CreatorFollowEntity newFollow = new CreatorFollowEntity();
                    newFollow.setCustomerEntity(customerEntity);
                    newFollow.setCreatorEntity(creatorEntity);
                    newFollow.setFollowStatus(false);
                    return newFollow;
                });

        // 팔로우 상태 변경
        followEntity.setFollowStatus(!Boolean.TRUE.equals(followEntity.getFollowStatus()));

        // 변경된 상태 저장
        creatorFollowRepository.save(followEntity);

        return CreatorFollowDTO.builder()
                .customerId(followDTO.getCustomerId())
                .creatorId(followDTO.getCreatorId())
                .followStatus(followEntity.getFollowStatus())
                .build();
    }
}

