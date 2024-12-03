package com.myproject.callabo_user_boot.product.service;

import com.myproject.callabo_user_boot.product.dto.ProductListDTO;
import com.myproject.callabo_user_boot.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductListDTO> getProductList(String creatorId) {
        return productRepository.productList(creatorId);
    }
}
