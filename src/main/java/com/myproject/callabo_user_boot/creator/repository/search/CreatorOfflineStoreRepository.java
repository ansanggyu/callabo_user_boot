package com.myproject.callabo_user_boot.creator.repository.search;

import com.myproject.callabo_user_boot.creator.domain.CreatorOfflineStoreEntity;
import com.myproject.callabo_user_boot.creator.dto.OfflineStoreListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatorOfflineStoreRepository extends JpaRepository<CreatorOfflineStoreEntity, Long> {
    // 모든 오프라인 스토어 리스트 반환
    @Query("""
    SELECT new com.myproject.callabo_user_boot.creator.dto.OfflineStoreListDTO(
        store.storeNo,
        store.storeName,
        store.storeAddress,
        store.storeImage,
        CAST(store.latitude AS string),
        CAST(store.longitude AS string)
    )
    FROM CreatorOfflineStoreEntity store
    ORDER BY store.storeNo DESC
""")
    List<OfflineStoreListDTO> findAllOfflineStores();

}