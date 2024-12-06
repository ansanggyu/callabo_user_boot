package com.myproject.callabo_user_boot.review.repository.search;

import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;

import java.util.List;

public interface ReviewSearch {
    List<ReviewListDTO> reviewListByCreator(String creatorId);
}
