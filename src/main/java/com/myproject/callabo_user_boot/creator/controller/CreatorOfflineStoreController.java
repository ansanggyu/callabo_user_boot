package com.myproject.callabo_user_boot.creator.controller;

import com.myproject.callabo_user_boot.creator.dto.OfflineStoreListDTO;
import com.myproject.callabo_user_boot.creator.service.CreatorOfflineStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api2/offlinestore")
@RequiredArgsConstructor
public class CreatorOfflineStoreController {

    private final CreatorOfflineStoreService creatorOfflineStoreService;

    @GetMapping("/list")
    public ResponseEntity<List<OfflineStoreListDTO>> getAllOfflineStores() {
        List<OfflineStoreListDTO> stores = creatorOfflineStoreService.getAllOfflineStores();
        return ResponseEntity.ok(stores);
    }


}