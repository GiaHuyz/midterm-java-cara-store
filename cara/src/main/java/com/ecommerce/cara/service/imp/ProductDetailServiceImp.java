package com.ecommerce.cara.service.imp;

import com.ecommerce.cara.dto.DetailDTO;
import com.ecommerce.cara.dto.ProductDTO;
import com.ecommerce.cara.entity.*;
import com.ecommerce.cara.payload.request.DetailRequest;
import com.ecommerce.cara.payload.request.ProductRequest;
import com.ecommerce.cara.repository.ProductDetailRepository;
import com.ecommerce.cara.repository.ProductRepository;
import com.ecommerce.cara.service.ProductDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImp implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailsRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public DetailDTO save(DetailRequest detailRequest) {
        ProductDetails productDetails;
        if (detailRequest.getDetailId() != null) {
            productDetails = productDetailsRepository.findById(detailRequest.getDetailId())
                    .orElseThrow(() -> new EntityNotFoundException("Product Detail not found"));
        } else {
            Product product = productRepository.findById(detailRequest.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            productDetails = new ProductDetails();
            productDetails.setProduct(product);
        }

        productDetails.setColor(detailRequest.getColor());
        productDetails.setSize(detailRequest.getSize());

        ProductDetails savedProductDetail = productDetailsRepository.save(productDetails);

        DetailDTO detailDTO = new DetailDTO();
        detailDTO.setDetailId(savedProductDetail.getDetailId());
        detailDTO.setColors(savedProductDetail.getColor());
        detailDTO.setSizes(savedProductDetail.getSize());

        return detailDTO;
    }

    @Override
    public void delete(Integer id) {
        ProductDetails productDetails = productDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("item not found with id: " + id));

        productDetailsRepository.delete(productDetails);
    }
}
