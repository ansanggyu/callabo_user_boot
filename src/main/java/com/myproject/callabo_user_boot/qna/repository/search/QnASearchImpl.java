package com.myproject.callabo_user_boot.qna.repository.search;

import com.myproject.callabo_user_boot.customer.domain.QCustomerEntity;
import com.myproject.callabo_user_boot.qna.domain.QQnAEntity;
import com.myproject.callabo_user_boot.qna.domain.QnAEntity;
import com.myproject.callabo_user_boot.qna.dto.QnAListDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class QnASearchImpl extends QuerydslRepositorySupport implements QnASearch {

    public QnASearchImpl() {super(QnAEntity.class);}

    @Override
    public List<QnAListDTO> QnAList(Long qnaNo) {

        QQnAEntity qna = QQnAEntity.qnAEntity;
        QCustomerEntity customer = QCustomerEntity.customerEntity;

        JPQLQuery<QnAListDTO> query = from(qna)
                .leftJoin(customer).on(qna.customerEntity.eq(customer))
                .select(Projections.bean(QnAListDTO.class,
                        qna.qnaNo,
                        qna.question,
                        customer.customerId,
                        qna.createdAt
                        ));

        // 조건 추가 (qnaNo가 null 아닐 경우)
        if (qnaNo != null) {
            query.where(qna.qnaNo.eq(qnaNo));
        }

        // 정렬 추가 (createdAt 내림차순)
        query.orderBy(qna.createdAt.desc());

        return query.fetch();
    }
}
