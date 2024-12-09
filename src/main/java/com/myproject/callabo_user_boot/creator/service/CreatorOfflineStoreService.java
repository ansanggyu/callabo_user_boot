package com.myproject.callabo_user_boot.creator.service;

import com.myproject.callabo_user_boot.creator.dto.OfflineStoreListDTO;
import com.myproject.callabo_user_boot.creator.repository.search.CreatorOfflineStoreRepository;
import com.myproject.callabo_user_boot.creator.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorOfflineStoreService {
    private static final Logger log = LoggerFactory.getLogger(CreatorOfflineStoreService.class);
    private final CreatorRepository creatorRepository;
    private final CreatorOfflineStoreRepository creatorOfflineStoreRepository;

    // 모든 오프라인 스토어 리스트 반환
    public List<OfflineStoreListDTO> getAllOfflineStores() {
        return creatorOfflineStoreRepository.findAllOfflineStores();
    }
}
