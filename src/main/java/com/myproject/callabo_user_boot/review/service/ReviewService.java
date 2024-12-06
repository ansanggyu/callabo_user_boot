package com.myproject.callabo_user_boot.review.service;

import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;
import com.myproject.callabo_user_boot.review.repository.ReviewRepository;
import com.myproject.callabo_user_boot.review.repository.search.ReviewSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewSearch reviewSearch;

    public List<ReviewListDTO> getReviewList(String creatorId, Long productNo) {
        if (productNo != null) {
            log.info("Fetching reviews for productNo: {}", productNo);
            return reviewSearch.reviewListByProduct(productNo);
        }
        log.info("Fetching reviews for creatorId: {}", creatorId);
        return reviewSearch.reviewListByCreator(creatorId);
    }


}
