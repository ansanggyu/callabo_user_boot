package com.myproject.callabo_user_boot.product.repository.search;

import com.myproject.callabo_user_boot.product.dto.ProductListDTO;

import java.util.List;

public interface ProductSearch {

    List<ProductListDTO> productList(String creatorId);
}
