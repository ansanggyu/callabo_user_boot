package com.myproject.callabo_user_boot.creator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineStoreListDTO {
    private Long storeNo;
    private String storeName;
    private String storeAddress;
    private String storeImage;
    private String latitude;
    private String longitude;
}
