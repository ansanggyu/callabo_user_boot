package com.myproject.callabo_user_boot.order.service;

import com.myproject.callabo_user_boot.creator.domain.CreatorEntity;
import com.myproject.callabo_user_boot.creator.repository.CreatorRepository;
import com.myproject.callabo_user_boot.customer.domain.CustomerEntity;
import com.myproject.callabo_user_boot.customer.repository.CustomerRepository;
import com.myproject.callabo_user_boot.order.domain.OrderItemEntity;
import com.myproject.callabo_user_boot.order.domain.OrderStatus;
import com.myproject.callabo_user_boot.order.domain.OrdersEntity;
import com.myproject.callabo_user_boot.order.dto.OrderItemDTO;
import com.myproject.callabo_user_boot.order.dto.OrderItemRequestDTO;
import com.myproject.callabo_user_boot.order.dto.OrderRequestDTO;
import com.myproject.callabo_user_boot.order.dto.OrdersDTO;
import com.myproject.callabo_user_boot.order.repository.OrderItemRepository;
import com.myproject.callabo_user_boot.order.repository.OrderRepository;
import com.myproject.callabo_user_boot.product.domain.ProductEntity;
import com.myproject.callabo_user_boot.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;       // OrdersEntity 관리
    private final OrderItemRepository orderItemRepository; // OrderItemEntity 관리
    private final CustomerRepository customerRepository; // CustomerEntity 조회
    private final CreatorRepository creatorRepository;   // CreatorEntity 조회
    private final ProductRepository productRepository;   // ProductEntity 조회

    @Transactional
    public List<OrdersEntity> createOrders(List<OrderRequestDTO> orderRequests) {
        // 1. CustomerEntity와 CreatorEntity를 미리 로드
        String customerId = orderRequests.get(0).getCustomerId();
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customerId: " + customerId));

        String creatorId = orderRequests.get(0).getCreatorId();
        CreatorEntity creatorEntity = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid creatorId: " + creatorId));

        // 2. ProductEntity를 한 번에 로드
        List<Long> productIds = orderRequests.stream()
                .flatMap(order -> order.getItems().stream())
                .map(OrderItemRequestDTO::getProductNo)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, ProductEntity> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(ProductEntity::getProductNo, product -> product));

        // 3. OrdersEntity 및 OrderItemEntity 생성
        List<OrdersEntity> ordersEntities = new ArrayList<>();
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();

        for (OrderRequestDTO orderRequest : orderRequests) {
            OrdersEntity ordersEntity = new OrdersEntity();
            ordersEntity.setCustomerEntity(customerEntity);
            ordersEntity.setCreatorEntity(creatorEntity);
            ordersEntity.setCustomerAddress(orderRequest.getCustomerAddress());
            ordersEntity.setCustomerAddrDetail(orderRequest.getCustomerAddrDetail());
            ordersEntity.setRecipientName(orderRequest.getRecipientName());
            ordersEntity.setRecipientPhone(orderRequest.getRecipientPhone());
            ordersEntity.setStatus(OrderStatus.PENDING);

            int totalAmount = 0;
            int totalPrice = 0;

            for (OrderItemRequestDTO itemDTO : orderRequest.getItems()) {
                ProductEntity product = productMap.get(itemDTO.getProductNo());

                if (product == null) {
                    throw new IllegalArgumentException("Invalid productNo: " + itemDTO.getProductNo());
                }

                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setProductEntity(product);
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setUnitPrice(itemDTO.getUnitPrice());
                orderItem.setOrdersEntity(ordersEntity);

                // OrdersEntity에 OrderItem 추가
                ordersEntity.getOrderItems().add(orderItem);

                totalAmount += itemDTO.getQuantity();
                totalPrice += itemDTO.getQuantity() * itemDTO.getUnitPrice();

                orderItemEntities.add(orderItem);
            }


            ordersEntity.setTotalAmount(totalAmount);
            ordersEntity.setTotalPrice(totalPrice);
            ordersEntities.add(ordersEntity);
        }

        // 4. Batch Insert로 OrdersEntity와 OrderItemEntity 저장
        orderRepository.saveAll(ordersEntities);
        orderItemRepository.saveAll(orderItemEntities);

        return ordersEntities;
    }

    @Transactional
    public List<OrdersDTO> createOrdersAndConvertToDTO(List<OrderRequestDTO> orderRequests) {
        List<OrdersEntity> ordersEntities = createOrders(orderRequests);
        return ordersEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrdersDTO convertToDTO(OrdersEntity ordersEntity) {
        OrdersDTO dto = new OrdersDTO();
        dto.setOrderNo(ordersEntity.getOrderNo());
        dto.setRecipientName(ordersEntity.getRecipientName());
        dto.setRecipientPhone(ordersEntity.getRecipientPhone());
        dto.setCustomerAddress(ordersEntity.getCustomerAddress());
        dto.setCustomerAddrDetail(ordersEntity.getCustomerAddrDetail());
        dto.setTotalAmount(ordersEntity.getTotalAmount());
        dto.setTotalPrice(ordersEntity.getTotalPrice());

        List<OrderItemDTO> items = ordersEntity.getOrderItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductNo(item.getProductEntity().getProductNo());
            itemDTO.setProductName(item.getProductEntity().getProductName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setUnitPrice(item.getUnitPrice());
            return itemDTO;
        }).collect(Collectors.toList());
        dto.setItems(items);

        return dto;
    }
}
