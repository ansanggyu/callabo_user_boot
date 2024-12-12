package com.myproject.callabo_user_boot.creator.repository.search;

import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.creator.domain.QCreatorEntity;
import com.myproject.callabo_user_boot.creator.dto.CreatorListDTO;
import com.myproject.callabo_user_boot.customer.domain.QCreatorFollowEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class CreatorSearchImpl extends QuerydslRepositorySupport implements CreatorSearch {

    public CreatorSearchImpl() {super(CreatorEntity.class);}

    @Override
    public List<CreatorListDTO> getCreatorList() {

        QCreatorEntity creator = QCreatorEntity.creatorEntity;
        QCreatorFollowEntity follow = QCreatorFollowEntity.creatorFollowEntity;

        // 팔로워 수를 계산하는 서브쿼리
        JPQLQuery<Long> followerCountSubquery = JPAExpressions
                .select(follow.count())
                .from(follow)
                .where(follow.creatorEntity.eq(creator)
                        .and(follow.followStatus.isTrue()));

        // 메인 쿼리
        JPQLQuery<CreatorListDTO> query = from(creator)
                .leftJoin(follow).on(
                        follow.creatorEntity.eq(creator)
                )
                .select(Projections.bean(
                        CreatorListDTO.class,
                        creator.creatorId,
                        creator.creatorName,
                        creator.backgroundImg,
                        creator.logoImg,
                        follow.followStatus.coalesce(false).as("followStatus"), // followStatus 없으면 false
                        Expressions.as(followerCountSubquery, "followerCount") // 팔로워 수 계산 결과를 followerCount로 매핑
                ));

        return query.fetch();
    }

    // 필로우 상태 변경
}
