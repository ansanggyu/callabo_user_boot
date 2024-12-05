package com.myproject.callabo_user_boot.creator.controller;

import com.myproject.callabo_user_boot.creator.dto.CreatorListDTO;
import com.myproject.callabo_user_boot.creator.service.CreatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        List<CreatorListDTO> creators = creatorService.getCreatorsList();
        return ResponseEntity.ok(creators);
    }
}
