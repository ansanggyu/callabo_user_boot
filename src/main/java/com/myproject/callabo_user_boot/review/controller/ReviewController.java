package com.myproject.callabo_user_boot.review.controller;

import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;
import com.myproject.callabo_user_boot.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api2/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list")
    public ResponseEntity<List<ReviewListDTO>> getReviewList(@RequestParam("creatorId") String creatorId) {
        List<ReviewListDTO> response = reviewService.getReviewList(creatorId);
        return ResponseEntity.ok(response);
    }

}
