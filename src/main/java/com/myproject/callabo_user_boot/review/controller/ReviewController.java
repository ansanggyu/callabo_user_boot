package com.myproject.callabo_user_boot.review.controller;

import com.myproject.callabo_user_boot.review.dto.ReviewListDTO;
import com.myproject.callabo_user_boot.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api2/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

//    @GetMapping("/list")
//    public ResponseEntity<List<ReviewListDTO>> getReviewList(@RequestParam("creatorId") String creatorId) {
//        List<ReviewListDTO> response = reviewService.getReviewList(creatorId);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/list")
    public ResponseEntity<List<ReviewListDTO>> getReviewList(
            @RequestParam("creatorId") String creatorId,
            @RequestParam(value = "productNo", required = false) Long productNo) {

        log.info("creatorId: {}, productNo: {}", creatorId, productNo);

        List<ReviewListDTO> response = reviewService.getReviewList(creatorId, productNo);
        return ResponseEntity.ok(response);
    }


}
