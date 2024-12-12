package com.myproject.callabo_user_boot.review.service;

import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.creator.repository.CreatorRepository;
import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import com.myproject.callabo_user_boot.customer.repository.CustomerRepository;
import com.myproject.callabo_user_boot.product.domain.ProductEntity;
import com.myproject.callabo_user_boot.product.repository.ProductRepository;
import com.myproject.callabo_user_boot.review.domain.ReviewEntity;
import com.myproject.callabo_user_boot.review.domain.ReviewImageEntity;
import com.myproject.callabo_user_boot.review.dto.ReviewImageDTO;
import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;
import com.myproject.callabo_user_boot.review.dto.ReviewRegisterDTO;
import com.myproject.callabo_user_boot.review.repository.ReviewRepository;
import com.myproject.callabo_user_boot.review.repository.search.ReviewSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewSearch reviewSearch;
    private final CustomerRepository customerRepository;
    private final CreatorRepository creatorRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    // review 등록
    public Long registerReview(ReviewRegisterDTO reviewRegisterDTO) {

        ProductEntity product = productRepository.findById(reviewRegisterDTO.getProductNo())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CustomerEntity customer = customerRepository.findById(reviewRegisterDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CreatorEntity creator = creatorRepository.findById(reviewRegisterDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        // ReviewEntity 생성
        ReviewEntity reviewEntity = addReviewEntity(reviewRegisterDTO, customer, product, creator);

        // 이미지 처리 및 저장
        if (reviewRegisterDTO.getReviewImages() != null && !reviewRegisterDTO.getReviewImages().isEmpty()) {
            List<ReviewImageEntity> reviewImages = reviewRegisterDTO.getReviewImages().stream()
                    .map(imageDto -> ReviewImageEntity.builder()
                            .reviewEntity(reviewEntity)
                            .reviewImageUrl(imageDto.getReviewImageUrl())
                            .reviewImageOrd(imageDto.getReviewImageOrd())
                            .build())
                    .collect(Collectors.toList());

            // ReivewEntity와 연관 관계 설정
            reviewImages.forEach(reviewEntity::addReviewImage);
        }

        reviewRepository.save(reviewEntity);

        return reviewEntity.getReviewNo();
    }

    private ReviewEntity addReviewEntity(ReviewRegisterDTO dto, CustomerEntity customer, ProductEntity product, CreatorEntity creator) {

        return ReviewEntity.builder()
                .customerEntity(customer)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .creatorEntity(creator)
                .productEntity(product)
                .build();
    }

    public List<ReviewImageDTO> saveReviewImages(Long reviewNo, List<ReviewImageDTO> reviewImageDTOs) {
        ReviewEntity review = entityManager.find(ReviewEntity.class, reviewNo);
        if (review != null) {
            throw new IllegalArgumentException("Invalid review no: " + reviewNo);
        }

        List<ReviewImageDTO> savedImageDTOs = new ArrayList<>();

        for (ReviewImageDTO reviewImageDTO : reviewImageDTOs) {
            ReviewImageEntity imageEntity = ReviewImageEntity.builder()
                    .reviewImageUrl(reviewImageDTO.getReviewImageUrl())
                    .reviewImageOrd(reviewImageDTO.getReviewImageOrd())
                    .reviewEntity(review)
                    .build();

            entityManager.persist(imageEntity);

            savedImageDTOs.add(ReviewImageDTO.builder()
                    .reviewImageUrl(imageEntity.getReviewImageUrl())
                    .reviewImageOrd(imageEntity.getReviewImageOrd())
                    .build());
        }

        return reviewImageDTOs;
    }

    public List<ReviewListDTO> getReviewList(String creatorId, Long productNo) {
        if (productNo != null) {
            log.info("Fetching reviews for productNo: {}", productNo);
            return reviewSearch.reviewListByProduct(productNo);
        }
        log.info("Fetching reviews for creatorId: {}", creatorId);
        return reviewSearch.reviewListByCreator(creatorId);
    }

    public List<ReviewListDTO> getReviewsByCustomerId(String customerId) {
        return reviewSearch.reviewListByCustomer(customerId);
    }

}
