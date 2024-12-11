package com.myproject.callabo_user_boot.order.service;

import com.myproject.callabo_user_boot.order.domain.OrderItemEntity;
import com.myproject.callabo_user_boot.order.domain.OrdersEntity;
import com.myproject.callabo_user_boot.order.dto.OrderItemListDTO;
import com.myproject.callabo_user_boot.order.dto.OrderListDTO;
import com.myproject.callabo_user_boot.order.repository.OrderRepository;
import com.myproject.callabo_user_boot.product.domain.ProductImageEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderListService {
    private final OrderRepository orderRepository;

    public OrderListService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // DTO로 변환 메서드
    private OrderItemListDTO toOrderItemListDTO(OrderItemEntity itemEntity) {
        OrderItemListDTO itemDTO = new OrderItemListDTO();
        itemDTO.setProductNo(itemEntity.getProductEntity().getProductNo());
        itemDTO.setProductName(itemEntity.getProductEntity().getProductName());

        // 첫 번째 이미지를 대표 이미지로 설정
        String productImageUrl = itemEntity.getProductEntity().getProductImages().stream()
                .findFirst()
                .map(ProductImageEntity::getProductImageUrl)
                .orElse("https://via.placeholder.com/150"); // 기본 이미지 URL
        itemDTO.setProductImage(productImageUrl);

        itemDTO.setUnitPrice(itemEntity.getUnitPrice());
        itemDTO.setQuantity(itemEntity.getQuantity());
        return itemDTO;
    }

    private OrderListDTO toOrderListDTO(OrdersEntity ordersEntity) {
        OrderListDTO orderDTO = new OrderListDTO();
        orderDTO.setOrderNo(ordersEntity.getOrderNo());
        orderDTO.setOrderDate(ordersEntity.getCreatedAt().toString());
        orderDTO.setCreatorId(ordersEntity.getCreatorEntity().getCreatorId());
        orderDTO.setCreatorName(ordersEntity.getCreatorEntity().getCreatorName());
        orderDTO.setCustomerName(ordersEntity.getCustomerEntity().getCustomerName());
        orderDTO.setTotalAmount(ordersEntity.getTotalAmount());
        orderDTO.setTotalPrice(ordersEntity.getTotalPrice());
        orderDTO.setStatus(ordersEntity.getStatus().name());

        // OrderItems를 변환하여 설정
        orderDTO.setItems(ordersEntity.getOrderItems().stream()
                .map(this::toOrderItemListDTO)
                .collect(Collectors.toList()));
        return orderDTO;
    }

    // 고객 ID를 기반으로 주문 목록 가져오기
    public List<OrderListDTO> getOrdersByCustomerId(String customerId) {
        List<OrdersEntity> ordersEntities = orderRepository.findByCustomerEntity_CustomerId(customerId,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return ordersEntities.stream()
                .map(this::toOrderListDTO)
                .collect(Collectors.toList());
    }
}