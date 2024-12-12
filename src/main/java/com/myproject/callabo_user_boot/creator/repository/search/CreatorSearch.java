package com.myproject.callabo_user_boot.creator.repository.search;

import com.myproject.callabo_user_boot.creator.dto.CreatorListDTO;

import java.util.List;

public interface CreatorSearch {

    // 제작자 리스트
    List<CreatorListDTO> getCreatorList();
}
