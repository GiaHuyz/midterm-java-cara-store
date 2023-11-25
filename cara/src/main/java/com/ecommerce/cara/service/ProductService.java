package com.ecommerce.cara.service;

import com.ecommerce.cara.dto.ProductAdminDTO;
import com.ecommerce.cara.dto.ProductDTO;
import com.ecommerce.cara.dto.ProductDetailsDTO;
import com.ecommerce.cara.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> getAllProducts(Integer brand, Integer category, String sort, Double minPrice, Double maxPrice, String size, int page, String keyword);
    ProductDetailsDTO getProductDetailsById(int id);
    Page<ProductAdminDTO> getAllProductsForAdmin(PageRequest pageRequest, String searchValue);
    void delete(Integer id);
    ProductDTO saveProduct(ProductRequest productRequest);
}
