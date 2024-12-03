package com.myproject.callabo_user_boot.product.controller;

import com.myproject.callabo_user_boot.product.dto.ProductListDTO;
import com.myproject.callabo_user_boot.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api2/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductListDTO>> getProductList(String creatorId){

        log.info("제작자 아이디: " + creatorId);
        List<ProductListDTO> products = productService.getProductList(creatorId);
        return ResponseEntity.ok(products);
    }
}
