package com.myproject.callabo_user_boot.order.domain;

import com.myproject.callabo_user_boot.common.BasicEntity;
import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class OrdersEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_no", nullable = false)
    private Long orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private CreatorEntity creatorEntity;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "customer_addr_detail", nullable = false)
    private String customerAddrDetail;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone", nullable = false)
    private String recipientPhone;

    @PrePersist
    @PreUpdate
    private void calculateTotals() {
        this.totalAmount = this.orderItems.stream().mapToInt(OrderItemEntity::getQuantity).sum();
        this.totalPrice = this.orderItems.stream().mapToInt(item ->
                item.getProductEntity().getProductPrice() * item.getQuantity()).sum();
    }

    @OneToMany(mappedBy = "ordersEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();
}