package com.myproject.callabo_user_boot.review.domain;

import com.myproject.callabo_user_boot.qna.domain.QnAEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_image")
public class ReviewImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_no")
    private Integer reviewImageNo;

    @Column(name = "review_image_url", length = 2000)
    private String reviewImageUrl;

    @Column(name = "review_image_ord")
    private Integer reviewImageOrd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_no", referencedColumnName = "review_no")
    private ReviewEntity reviewEntity;

    public void linkToReview(ReviewEntity reviewEntity) {
        this.reviewEntity = reviewEntity;
    }
}
