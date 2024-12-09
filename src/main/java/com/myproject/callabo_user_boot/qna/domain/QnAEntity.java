package com.myproject.callabo_user_boot.qna.domain;

import com.myproject.callabo_user_boot.common.BasicEntity;
import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import com.myproject.callabo_user_boot.product.domain.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "qna")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QnAEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="qna_no", nullable = false)
    private Long qnaNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creatorEntity;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer")
    private String answer;

    @OneToMany(mappedBy = "qnaEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QnAImageEntity> qnAImages = new ArrayList<>();

    public void changeQuestion(String question) { this.question = question; }

    public QnAEntity changeQnAImages(List<QnAImageEntity> qnAImages) {
        this.qnAImages.clear(); // 기존 이미지 삭제
        this.qnAImages.addAll(qnAImages); // 새로운 이미지 추가
        return this;
    }
}
