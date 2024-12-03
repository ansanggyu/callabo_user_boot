package com.myproject.callabo_user_boot.creator.domain;

import com.myproject.callabo_user_boot.common.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

    @Column(name = "background_img")
    private String backgroundImg;

    @Column(name = "logo_img")
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
}
