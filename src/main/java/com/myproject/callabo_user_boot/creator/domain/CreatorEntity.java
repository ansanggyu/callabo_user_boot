package com.myproject.callabo_user_boot.creator.domain;

import com.myproject.callabo_user_boot.common.BasicEntity;
import com.myproject.callabo_user_boot.order.domain.OrdersEntity;
import com.myproject.callabo_user_boot.qna.domain.QnAEntity;
import com.myproject.callabo_user_boot.review.domain.ReviewEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "creator")
public class CreatorEntity extends BasicEntity {

    @Id
    @Column(name = "creator_id", nullable = false)
    private String creatorId;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @Column(name = "creator_email", nullable = false)
    private String creatorEmail;

    @Column(name = "creator_phone", nullable = false)
    private String creatorPhone;

    @Column(name = "creator_password", nullable = false)
    private String creatorPassword;

    @Column(name = "background_img", length = 2000)
    private String backgroundImg;

    @Column(name = "logo_img", length = 2000)
    private String logoImg;

    @Column(name = "del_flag", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean delFlag = Boolean.FALSE;

    @Column(name = "email_notifications", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean emailNotifications = Boolean.TRUE;

    @Column(name = "sms_notifications", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean smsNotifications = Boolean.TRUE;

    @Column(name = "creator_account")
    private String creatorAccount;

    @Column(name = "creator_bank")
    private String creatorBank;

    // Creator와 연관된 QnA
    @OneToMany(mappedBy = "creatorEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QnAEntity> qnaEntities = new ArrayList<>();

    // Creator와 연관된 Review
    @OneToMany(mappedBy = "creatorEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviewEntities = new ArrayList<>();

    // Creator와 연관된 Order
    @OneToMany(mappedBy = "creatorEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersEntity> orderEntities = new ArrayList<>();

    // Creator와 연관된 OfflineStore
    @OneToMany(mappedBy = "creatorEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreatorOfflineStoreEntity> offlineStoreEntities = new ArrayList<>();
}
