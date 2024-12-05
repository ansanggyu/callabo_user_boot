package com.myproject.callabo_user_boot.qna.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "qna_image")
public class QnAImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_image_no")
    private Integer qnaImageNo;

    @Column(name = "qna_image_url", length = 512)
    private String qnaImageUrl;

    @Column(name = "qna_image_ord")
    private Integer qnaImageOrd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_no", referencedColumnName = "qna_no")
    private QnAEntity qnaEntity;

}
