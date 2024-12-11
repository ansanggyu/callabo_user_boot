package com.myproject.callabo_user_boot.review.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

}
