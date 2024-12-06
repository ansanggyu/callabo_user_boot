package com.myproject.callabo_user_boot.review.service;

import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;
import com.myproject.callabo_user_boot.review.repository.ReviewRepository;
import com.myproject.callabo_user_boot.review.repository.search.ReviewSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewSearch reviewSearch;

    public List<ReviewListDTO> getReviewList(String creatorId) {
        return reviewSearch.reviewListByCreator(creatorId);
    }


}
