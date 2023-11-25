package com.ecommerce.cara.service;

import com.ecommerce.cara.dto.DetailDTO;
import com.ecommerce.cara.dto.ProductDTO;
import com.ecommerce.cara.entity.ProductDetails;
import com.ecommerce.cara.payload.request.DetailRequest;
import com.ecommerce.cara.payload.request.ProductRequest;

public interface ProductDetailService {
    DetailDTO save(DetailRequest detailRequest);

    void delete(Integer id);
}
