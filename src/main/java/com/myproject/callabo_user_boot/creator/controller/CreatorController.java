package com.myproject.callabo_user_boot.creator.controller;

import com.myproject.callabo_user_boot.customer.dto.CreatorFollowDTO;
import com.myproject.callabo_user_boot.creator.dto.CreatorListDTO;
import com.myproject.callabo_user_boot.creator.service.CreatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api2/creator")
@RequiredArgsConstructor
@Log4j2
public class CreatorController {

    private final CreatorService creatorService;

    // 제작자 리스트
    @GetMapping("/list")
    public ResponseEntity<List<CreatorListDTO>> getCreatorList() {
        List<CreatorListDTO> creators = creatorService.getCreatorList();
        return ResponseEntity.ok(creators);
    }

    // 팔로우 상태 변경
    @PostMapping("/follow")
    public ResponseEntity<Void> toggleFollowStatus(@RequestBody CreatorFollowDTO creatorFollowDTO) {
        try {
            creatorService.toggleFollowStatus(creatorFollowDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("팔로우 상태 변경 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 고객의 제작자 팔로우 상태 확인
    @GetMapping("/follow/status")
    public ResponseEntity<Boolean> checkFollowStatus(
            @RequestParam("customerId") String customerId,
            @RequestParam("creatorId") String creatorId) {
        try {
            boolean isFollowing = creatorService.checkFollowStatus(customerId, creatorId);
            return ResponseEntity.ok(isFollowing);
        } catch (Exception e) {
            log.error("팔로우 상태 확인 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
