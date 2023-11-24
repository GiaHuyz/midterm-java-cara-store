package com.ecommerce.cara.service;

import com.ecommerce.cara.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
    boolean updateProductImage(Integer productId, String oldFile, MultipartFile imageFile);
    boolean addProductImage(Integer productId, MultipartFile imageFile);
    void delete(Integer id, String filename);
}
