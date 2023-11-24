package com.ecommerce.cara.service.imp;

import com.ecommerce.cara.entity.Images;
import com.ecommerce.cara.entity.Product;
import com.ecommerce.cara.entity.ProductDetails;
import com.ecommerce.cara.repository.ImagesRepository;
import com.ecommerce.cara.repository.ProductRepository;
import com.ecommerce.cara.service.FileService;
import com.ecommerce.cara.service.ImagesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImagesServiceImp implements ImagesService {

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Override
    public boolean updateProductImage(Integer productId, String oldFile, MultipartFile imageFile) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            String imageName = imageFile.getOriginalFilename();
            Optional<Images> oldImage = imagesRepository.findByImageNameAndProduct(oldFile, product);
            Optional<Images> newImage = imagesRepository.findByImageNameAndProduct(imageName, product);

            if (oldImage.isPresent() && newImage.isEmpty()) {
                if(fileService.saveFile(imageFile)) {
                    oldImage.get().setImageName(imageName);
                    oldImage.get().setProduct(product);

                    imagesRepository.save(oldImage.get());
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addProductImage(Integer productId, MultipartFile imageFile) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            String imageName = imageFile.getOriginalFilename();
            Optional<Images> existingImage = imagesRepository.findByImageNameAndProduct(imageName, product);

            if (existingImage.isPresent()) {
                throw new IllegalStateException("Image already exists for this product");
            }

            Images image = new Images();
            image.setImageName(imageName);
            image.setProduct(product);
            imagesRepository.save(image);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void delete(Integer productId, String filename) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Images images = imagesRepository.findByImageNameAndProduct(filename, product)
                .orElseThrow(() -> new IllegalArgumentException("item not found with name: " + filename));

        imagesRepository.delete(images);
    }
}
