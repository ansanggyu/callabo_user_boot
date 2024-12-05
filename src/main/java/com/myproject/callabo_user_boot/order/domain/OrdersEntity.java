package com.myproject.callabo_user_boot.order.domain;

import com.myproject.callabo_user_boot.common.BasicEntity;
import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "orders")
public class OrdersEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_no", nullable = false)
    private Long orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creatorEntity;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "customer_address_detail", nullable = false)
    private String customerAddressDetail;

    @OneToMany(mappedBy = "ordersEntity", fetch = FetchType.LAZY)
    private Set<OrderItemEntity> orderItems = new HashSet<>();
}