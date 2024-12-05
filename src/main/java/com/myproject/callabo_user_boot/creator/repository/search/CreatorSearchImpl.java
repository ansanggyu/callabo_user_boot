package com.myproject.callabo_user_boot.creator.repository.search;

import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.creator.domain.QCreatorEntity;
import com.myproject.callabo_user_boot.creator.dto.CreatorListDTO;
import com.myproject.callabo_user_boot.customer.domain.QCreatorFollowEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class CreatorSearchImpl extends QuerydslRepositorySupport implements CreatorSearch {

    public CreatorSearchImpl() {super(CreatorEntity.class);}

    @Override
    public List<CreatorListDTO> creatorsList() {

        QCreatorEntity creator = QCreatorEntity.creatorEntity;
        QCreatorFollowEntity follow = QCreatorFollowEntity.creatorFollowEntity;

        // JPQL Query 생성
        JPQLQuery<CreatorListDTO> query = from(creator)
                .leftJoin(follow).on(follow.creatorEntity.eq(creator))
                .groupBy(creator.creatorId, creator.creatorName, creator.backgroundImg, creator.logoImg)
                .select(Projections.bean(CreatorListDTO.class,
                        creator.creatorId,
                        creator.creatorName,
                        creator.backgroundImg,
                        creator.logoImg,
                        follow.followStatus
                ));

        return query.fetch();
    }

    // 필로우 상태 변경
}
