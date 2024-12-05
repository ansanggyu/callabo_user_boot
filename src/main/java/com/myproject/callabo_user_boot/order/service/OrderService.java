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
        List<OrdersEntity> ordersEntities = new ArrayList<>();

        for (OrderRequestDTO orderRequest : orderRequests) {
            // 1. CustomerEntity 조회
            CustomerEntity customerEntity = customerRepository.findById(orderRequest.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customerId: " + orderRequest.getCustomerId()));

            // 2. CreatorEntity 조회
            CreatorEntity creatorEntity = creatorRepository.findById(orderRequest.getCreatorId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid creatorId: " + orderRequest.getCreatorId()));

            // 3. OrdersEntity 생성 및 설정
            OrdersEntity ordersEntity = new OrdersEntity();
            ordersEntity.setCustomerEntity(customerEntity);
            ordersEntity.setCreatorEntity(creatorEntity);
            ordersEntity.setCustomerAddress(orderRequest.getCustomerAddress());
            ordersEntity.setCustomerAddrDetail(orderRequest.getCustomerAddrDetail());
            ordersEntity.setRecipientName(orderRequest.getRecipientName());
            ordersEntity.setRecipientPhone(orderRequest.getRecipientPhone());
            ordersEntity.setStatus(OrderStatus.PENDING);

            // 4. OrderItems 생성 및 추가
            List<OrderItemEntity> orderItems = new ArrayList<>();
            int totalAmount = 0; // 총 수량
            int totalPrice = 0;  // 총 가격

            for (OrderItemRequestDTO itemDTO : orderRequest.getItems()) {
                // ProductEntity 조회
                ProductEntity productEntity = productRepository.findById(itemDTO.getProductNo())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid productNo: " + itemDTO.getProductNo()));

                // OrderItem 생성
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setProductEntity(productEntity);
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setUnitPrice(itemDTO.getUnitPrice());
                orderItem.setOrdersEntity(ordersEntity);

                // 총 수량 및 총 가격 계산
                totalAmount += itemDTO.getQuantity();
                totalPrice += itemDTO.getQuantity() * itemDTO.getUnitPrice();

                orderItems.add(orderItem);
            }
            ordersEntity.setOrderItems(orderItems);

            // 5. 총 수량 및 총 가격 설정
            ordersEntity.setTotalAmount(totalAmount);
            ordersEntity.setTotalPrice(totalPrice);

            // 6. OrdersEntity 저장
            ordersEntities.add(orderRepository.save(ordersEntity));
        }

        return ordersEntities;
    }

    @Transactional
    public List<OrdersDTO> createOrdersAndConvertToDTO(List<OrderRequestDTO> orderRequests) {
        List<OrdersEntity> ordersEntities = createOrders(orderRequests);
        return ordersEntities.stream().map(order -> {
            OrdersDTO dto = new OrdersDTO();
            dto.setOrderNo(order.getOrderNo());
            dto.setRecipientName(order.getRecipientName());
            dto.setRecipientPhone(order.getRecipientPhone());
            dto.setCustomerAddress(order.getCustomerAddress());
            dto.setCustomerAddrDetail(order.getCustomerAddrDetail());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setTotalPrice(order.getTotalPrice());

            List<OrderItemDTO> items = order.getOrderItems().stream().map(item -> {
                OrderItemDTO itemDTO = new OrderItemDTO();
                itemDTO.setProductNo(item.getProductEntity().getProductNo());
                itemDTO.setProductName(item.getProductEntity().getProductName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setUnitPrice(item.getUnitPrice());
                return itemDTO;
            }).collect(Collectors.toList());
            dto.setItems(items);

            return dto;
        }).collect(Collectors.toList());
    }
}
