package com.myproject.callabo_user_boot.order.controller;

import com.myproject.callabo_user_boot.order.dto.OrderListDTO;
import com.myproject.callabo_user_boot.order.dto.OrderRequestDTO;
import com.myproject.callabo_user_boot.order.dto.OrdersDTO;
import com.myproject.callabo_user_boot.order.service.OrderListService;
import com.myproject.callabo_user_boot.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api2/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderListService orderListService;

    @PostMapping
    public ResponseEntity<List<OrdersDTO>> createOrders(@RequestBody List<OrderRequestDTO> orderRequests) {
        List<OrdersDTO> ordersDTOs = orderService.createOrdersAndConvertToDTO(orderRequests);
        return ResponseEntity.ok(ordersDTOs);
    }

    // 특정 고객의 주문 내역 조회
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderListDTO>> getOrdersByCustomer(@PathVariable String customerId) {
        List<OrderListDTO> orderList = orderListService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orderList);
    }
}