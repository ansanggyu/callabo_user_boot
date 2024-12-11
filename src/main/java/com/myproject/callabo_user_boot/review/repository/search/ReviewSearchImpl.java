package com.myproject.callabo_user_boot.review.repository.search;

import com.myproject.callabo_user_boot.creator.domain.QCreatorEntity;
import com.myproject.callabo_user_boot.product.domain.QProductEntity;
import com.myproject.callabo_user_boot.product.domain.QProductImageEntity;
import com.myproject.callabo_user_boot.product.dto.ProductImageDTO;
import com.myproject.callabo_user_boot.review.domain.QReviewEntity;
import com.myproject.callabo_user_boot.review.domain.QReviewImageEntity;
import com.myproject.callabo_user_boot.review.domain.ReviewEntity;
import com.myproject.callabo_user_boot.review.dto.ReviewImageDTO;
import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl() {
        super(ReviewEntity.class);
    }

    // 제작자 모든 상품 리뷰
    @Override
    public List<ReviewListDTO> reviewListByCreator(String creatorId) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QProductEntity product = QProductEntity.productEntity;
        QCreatorEntity creator = QCreatorEntity.creatorEntity;
        QReviewImageEntity reviewImage = QReviewImageEntity.reviewImageEntity;
        QProductImageEntity productImage = QProductImageEntity.productImageEntity;

        // 기본 리뷰 데이터 조회
        JPQLQuery<ReviewListDTO> query = from(review)
                .leftJoin(review.productEntity, product)
                .leftJoin(product.creatorEntity, creator)
                .where(review.creatorEntity.creatorId.eq(creatorId))
                .select(Projections.bean(ReviewListDTO.class,
                        review.reviewNo,
                        review.customerEntity.customerName.as("customerName"),
                        review.rating,
                        review.createdAt.as("createdAt"),
                        creator.creatorName.as("creatorName"),
                        product.productNo,
                        product.productPrice,
                        product.productName,
                        product.productDescription,
                        review.comment,
                        review.reply
                ));

        List<ReviewListDTO> dtoList = query.fetch();

        // 리뷰 이미지 데이터 조회
        List<Tuple> imageTuples = from(reviewImage)
                .join(reviewImage.reviewEntity, review)
                .select(
                        reviewImage.reviewEntity.reviewNo,
                        Projections.bean(ReviewImageDTO.class,
                                reviewImage.reviewImageNo,
                                reviewImage.reviewImageUrl,
                                reviewImage.reviewImageOrd
                        )
                )
                .fetch();

        // 상품 이미지 데이터 조회
        List<Tuple> productImageTuples = from(productImage)
                .join(productImage.productEntity, product) // productEntity 조인
                .select(
                        productImage.productEntity.productNo, // 상품 번호
                        Projections.bean(ProductImageDTO.class, // DTO 매핑
                                productImage.productImageNo,
                                productImage.productImageUrl,
                                productImage.productImageOrd
                        )
                )
                .fetch();

        // 리뷰 번호를 기준으로 ReviewImageDTO 리스트 매핑
        Map<Long, List<ReviewImageDTO>> reviewImageMap = imageTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(reviewImage.reviewEntity.reviewNo),
                        Collectors.mapping(
                                tuple -> tuple.get(1, ReviewImageDTO.class),
                                Collectors.toList()
                        )
                ));

        // 상품 번호를 기준으로 ProductImageDTO 리스트 매핑
        Map<Long, List<ProductImageDTO>> productImageMap = productImageTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(productImage.productEntity.productNo),
                        Collectors.mapping(
                                tuple -> tuple.get(1, ProductImageDTO.class),
                                Collectors.toList()
                        )
                ));

        // DTO에 리뷰 이미지 리스트 매핑
        dtoList.forEach(dto -> {
            Long reviewNo = dto.getReviewNo();
            Long productNo = dto.getProductNo();

            dto.setReviewImages(reviewImageMap.getOrDefault(reviewNo, Collections.emptyList()));
            dto.setProductImages(productImageMap.getOrDefault(productNo, Collections.emptyList()));
        });



        return dtoList;
    }

    // 해당 상품 리뷰
    @Override
    public List<ReviewListDTO> reviewListByProduct(Long productNo) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QProductEntity product = QProductEntity.productEntity;
        QCreatorEntity creator = QCreatorEntity.creatorEntity;
        QReviewImageEntity reviewImage = QReviewImageEntity.reviewImageEntity;
        QProductImageEntity productImage = QProductImageEntity.productImageEntity;

        // 기본 리뷰 데이터 조회
        JPQLQuery<ReviewListDTO> query = from(review)
                .leftJoin(review.productEntity, product)
                .leftJoin(product.creatorEntity, creator)
                .where(product.productNo.eq(productNo))
                .select(Projections.bean(ReviewListDTO.class,
                        review.reviewNo,
                        review.customerEntity.customerName.as("customerName"),
                        review.rating,
                        review.createdAt.as("createdAt"),
                        creator.creatorName.as("creatorName"),
                        product.productNo,
                        product.productPrice,
                        product.productName,
                        product.productDescription,
                        review.comment,
                        review.reply
                ));

        List<ReviewListDTO> dtoList = query.fetch();

        // 리뷰 이미지 데이터 조회
        List<Tuple> imageTuples = from(reviewImage)
                .join(reviewImage.reviewEntity, review)
                .select(
                        reviewImage.reviewEntity.reviewNo,
                        Projections.bean(ReviewImageDTO.class,
                                reviewImage.reviewImageNo,
                                reviewImage.reviewImageUrl,
                                reviewImage.reviewImageOrd
                        )
                )
                .fetch();

        // 상품 이미지 데이터 조회
        List<Tuple> productImageTuples = from(productImage)
                .join(productImage.productEntity, product) // productEntity 조인
                .select(
                        productImage.productEntity.productNo, // 상품 번호
                        Projections.bean(ProductImageDTO.class, // DTO 매핑
                                productImage.productImageNo,
                                productImage.productImageUrl,
                                productImage.productImageOrd
                        )
                )
                .fetch();

        // 리뷰 번호를 기준으로 ReviewImageDTO 리스트 매핑
        Map<Long, List<ReviewImageDTO>> reviewImageMap = imageTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(reviewImage.reviewEntity.reviewNo),
                        Collectors.mapping(
                                tuple -> tuple.get(1, ReviewImageDTO.class),
                                Collectors.toList()
                        )
                ));

        // 상품 번호를 기준으로 ProductImageDTO 리스트 매핑
        Map<Long, List<ProductImageDTO>> productImageMap = productImageTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(productImage.productEntity.productNo),
                        Collectors.mapping(
                                tuple -> tuple.get(1, ProductImageDTO.class),
                                Collectors.toList()
                        )
                ));

        // DTO에 리뷰 이미지 리스트 매핑
        dtoList.forEach(dto -> {
            Long reviewNo = dto.getReviewNo();
            dto.setReviewImages(reviewImageMap.getOrDefault(reviewNo, Collections.emptyList()));

            Long productNoKey = dto.getProductNo();
            dto.setProductImages(productImageMap.getOrDefault(productNoKey, Collections.emptyList()));
        });


        return dtoList;
    }

}
