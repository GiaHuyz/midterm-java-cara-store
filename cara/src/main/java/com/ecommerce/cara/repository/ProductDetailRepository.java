package com.ecommerce.cara.repository;

import com.ecommerce.cara.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetails, Integer> {
}
