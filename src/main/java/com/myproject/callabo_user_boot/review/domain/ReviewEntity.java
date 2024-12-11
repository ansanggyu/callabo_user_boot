package com.myproject.callabo_user_boot.review.domain;

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
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class ReviewEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_no", nullable = false)
    private Long reviewNo;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment", nullable = false , columnDefinition = "TEXT")
    private String comment;

    @Column(name = "reply")
    private String reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private ProductEntity productEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creatorEntity;

    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImageEntity> reviewImages = new ArrayList<>();

    public void addReviewImage(ReviewImageEntity reviewImage) {
        if (reviewImages == null) {
            reviewImages = new ArrayList<>();
        }

        reviewImages.add(reviewImage);
        reviewImage.linkToReview(this);
    }
}