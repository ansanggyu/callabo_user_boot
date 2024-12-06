package com.myproject.callabo_user_boot.review.repository.search;

import com.myproject.callabo_user_boot.creator.domain.QCreatorEntity;
import com.myproject.callabo_user_boot.product.domain.QProductEntity;
import com.myproject.callabo_user_boot.review.domain.QReviewEntity;
import com.myproject.callabo_user_boot.review.domain.QReviewImageEntity;
import com.myproject.callabo_user_boot.review.domain.ReviewEntity;
import com.myproject.callabo_user_boot.review.dto.ReviewImageDTO;
import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl() {
        super(ReviewEntity.class);
    }

    @Override
    public List<ReviewListDTO> reviewListByCreator(String creatorId) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QProductEntity product = QProductEntity.productEntity;
        QCreatorEntity creator = QCreatorEntity.creatorEntity;
        QReviewImageEntity reviewImage = QReviewImageEntity.reviewImageEntity;

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

        // 리뷰 번호를 기준으로 ReviewImageDTO 리스트 매핑
        Map<Long, List<ReviewImageDTO>> reviewImageMap = imageTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(reviewImage.reviewEntity.reviewNo),
                        Collectors.mapping(
                                tuple -> tuple.get(1, ReviewImageDTO.class),
                                Collectors.toList()
                        )
                ));

        // DTO에 리뷰 이미지 리스트 매핑
        dtoList.forEach(dto -> {
            Long reviewNo = dto.getReviewNo();
            dto.setReviewImages(reviewImageMap.getOrDefault(reviewNo, Collections.emptyList()));
        });

        return dtoList;
    }
}
