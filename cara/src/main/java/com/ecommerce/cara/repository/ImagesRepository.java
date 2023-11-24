package com.ecommerce.cara.repository;

import com.ecommerce.cara.entity.Images;
import com.ecommerce.cara.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImagesRepository extends JpaRepository<Images, Integer> {
    Optional<Images> findByImageNameAndProduct(String imageName, Product product);

    Optional<Images> findByImageName(String filename);
}
