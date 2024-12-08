package com.myproject.callabo_user_boot.product.service;

import com.myproject.callabo_user_boot.category.domain.CategoryEntity;
import com.myproject.callabo_user_boot.product.domain.ProductEntity;
import com.myproject.callabo_user_boot.product.domain.ProductImageEntity;
import com.myproject.callabo_user_boot.product.dto.ProductDetailDTO;
import com.myproject.callabo_user_boot.product.dto.ProductImageDTO;
import com.myproject.callabo_user_boot.product.dto.ProductListDTO;
import com.myproject.callabo_user_boot.product.repository.ProductRepository;
import com.myproject.callabo_user_boot.review.domain.ReviewEntity;
import com.myproject.callabo_user_boot.review.domain.ReviewImageEntity;
import com.myproject.callabo_user_boot.review.dto.ReviewImageDTO;
import com.myproject.callabo_user_boot.review.dto.ReviewReadDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 리스트
    public List<ProductListDTO> getProductList(String creatorId) {
        return productRepository.productList(creatorId);
    }

    // 상품 상세 조회
    public ProductDetailDTO readProductDetail(Long productNo) {

        // JPQL 쿼리 실행: 상품, 카테고리, 리뷰, 리뷰 이미지, 상품 이미지 데이터를 가져옴
        List<Object[]> result = productRepository.readProductDetails(productNo);

        // 조회된 데이터가 없으면 예외 발생
        if (result.isEmpty()) {
            throw new RuntimeException("해당하는 productNo 없음 : " + productNo);
        }

        // 첫 번째 행의 상품 및 카테고리 데이터 추출
        ProductEntity product = (ProductEntity) result.get(0)[0];
        CategoryEntity category = (CategoryEntity) result.get(0)[1];

        // 리뷰 리스트를 저장할 맵 생성 (리뷰 ID 기준 그룹화)
        Map<Long, ReviewReadDTO> reviewMap = new HashMap<>();
        for (Object[] row : result) {
            ReviewEntity review = (ReviewEntity) row[2]; // 리뷰 데이터
            ReviewImageEntity reviewImage = (ReviewImageEntity) row[3]; // 리뷰 이미지 데이터

            if (review != null) {
                // 리뷰가 없으면 새로 추가, 이미 존재하면 추가하지 않음 (리뷰 ID를 기준으로 동일한 리뷰가 여러 번 처리되지 않도록)
                reviewMap.putIfAbsent(review.getReviewNo(), ReviewReadDTO.builder()
                        .reviewNo(review.getReviewNo())
                        .rating(review.getRating())
                        .comment(review.getComment())
                        .createdAt(review.getCreatedAt())
                        .reviewImages(new ArrayList<>()) // 리뷰 이미지 리스트 초기화
                        .build());

                // 리뷰 이미지가 있으면 해당 리뷰에 추가
                if (reviewImage != null) {
                    reviewMap.get(review.getReviewNo()).getReviewImages().add(
                            new ReviewImageDTO(
                                    reviewImage.getReviewImageNo(),
                                    reviewImage.getReviewImageUrl(),
                                    reviewImage.getReviewImageOrd()
                            )
                    );
                }
            }
        }

        // 상품 이미지 데이터를 리스트로 변환
        List<ProductImageEntity> productImages = result.stream()
                .map(arr -> (ProductImageEntity) arr[4]) // 5번째 요소가 상품 이미지
                .filter(image -> image != null) // null 제외
                .distinct() // 중복 제거
                .collect(Collectors.toList());

        // ProductReadDTO 빌드 및 반환
        return ProductDetailDTO.builder()
                .productNo(product.getProductNo())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDescription(product.getProductDescription())
                .categoryName(category.getCategoryName())
                .productImages(productImages.stream().map(image -> new ProductImageDTO(
                       image.getProductImageNo(),
                       image.getProductImageUrl(),
                       image.getProductImageOrd()))
                    .collect(Collectors.toList()))
                .reviews(new ArrayList<>(reviewMap.values())) // 리뷰 리스트 매핑
                .build();
    }
}
