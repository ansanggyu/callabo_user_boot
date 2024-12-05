package com.myproject.callabo_user_boot.order.controller;

import com.myproject.callabo_user_boot.order.dto.OrderRequestDTO;
import com.myproject.callabo_user_boot.order.dto.OrdersDTO;
import com.myproject.callabo_user_boot.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<List<OrdersDTO>> createOrders(@RequestBody List<OrderRequestDTO> orderRequests) {
        List<OrdersDTO> ordersDTOs = orderService.createOrdersAndConvertToDTO(orderRequests);
        return ResponseEntity.ok(ordersDTOs);
    }

}
