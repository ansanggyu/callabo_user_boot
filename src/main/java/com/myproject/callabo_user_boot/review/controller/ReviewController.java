package com.myproject.callabo_user_boot.review.controller;

import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;
import com.myproject.callabo_user_boot.review.dto.ReviewRegisterDTO;
import com.myproject.callabo_user_boot.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api2/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/register")
    public ResponseEntity<Long> registerReview(@RequestBody ReviewRegisterDTO reviewRegisterDTO) {

        Long reviewNo = reviewService.registerReview(reviewRegisterDTO);

        return ResponseEntity.ok(reviewNo);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReviewListDTO>> getReviewList(
            @RequestParam(value = "creatorId", required = false) String creatorId,
            @RequestParam(value = "productNo", required = false) Long productNo) {

        log.info("creatorId: {}, productNo: {}", creatorId, productNo);

        List<ReviewListDTO> response = reviewService.getReviewList(creatorId, productNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<ReviewListDTO>> getCustomerReviews(
            @RequestParam String customerId) {

        log.info("Fetching reviews for customerId: {}", customerId);

        List<ReviewListDTO> response = reviewService.getReviewsByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }
}