package com.myproject.callabo_user_boot.creator.service;

import com.myproject.callabo_user_boot.creator.dto.CreatorListDTO;
import com.myproject.callabo_user_boot.creator.repository.CreatorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CreatorService {

    private final CreatorRepository creatorRepository;

    public List<CreatorListDTO> getCreatorsWithFollowStatus(String customerId) {
        return creatorRepository.getCreatorsWithFollowStatus(customerId);
    }
}
