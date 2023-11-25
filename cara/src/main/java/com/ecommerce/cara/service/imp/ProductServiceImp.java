package com.ecommerce.cara.service.imp;

import com.ecommerce.cara.dto.DetailDTO;
import com.ecommerce.cara.dto.ProductAdminDTO;
import com.ecommerce.cara.dto.ProductDTO;
import com.ecommerce.cara.dto.ProductDetailsDTO;
import com.ecommerce.cara.entity.*;
import com.ecommerce.cara.payload.request.ProductRequest;
import com.ecommerce.cara.repository.ProductDetailRepository;
import com.ecommerce.cara.repository.ProductRepository;
import com.ecommerce.cara.service.ProductService;
import com.ecommerce.cara.spec.ProductSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductDTO> getAllProducts(Integer brand, Integer category,
                                           String sort, Double minPrice,
                                           Double maxPrice, String size, int page, String keywords) {
        PageRequest pageRequest = PageRequest.of(page - 1, 3);
        if(keywords != null) {
            Page<Product> productPage = productRepository.search(keywords, pageRequest);
            return getProductDTOS(productPage);
        }
        Specification<Product> spec = ProductSpecifications.byCriteria(brand, category, sort, minPrice, maxPrice, size);
        Page<Product> productPage = productRepository.findAll(spec, pageRequest);

        return getProductDTOS(productPage);
    }

    private Page<ProductDTO> getProductDTOS(Page<Product> productPage) {
        return productPage.map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setProductName(product.getProductName());
            productDTO.setBrand(product.getBrand().getBrandName());
            productDTO.setCategory(product.getCategory().getCategoryName());
            productDTO.setPrice(product.getPrice());

            if (product.getImagesList() != null && !product.getImagesList().isEmpty()) {
                String images = product.getImagesList().get(0).getImageName();
                productDTO.setMainImage(images);
            }

            return productDTO;
        });
    }


    @Override
    public ProductDetailsDTO getProductDetailsById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDetailsDTO dto = new ProductDetailsDTO();
            List<String> images = new ArrayList<>();
            List<DetailDTO> details = new ArrayList<>();

            dto.setProductId(product.getProductId());
            dto.setProductName(product.getProductName());
            dto.setBrand(product.getBrand().getBrandName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setCategory(product.getCategory().getCategoryName());

            for (Images img: product.getImagesList()) {
                images.add(img.getImageName());
            }

            for (ProductDetails productDetails: product.getProductDetailsList()) {
                DetailDTO detailDTO = new DetailDTO();
                detailDTO.setDetailId(productDetails.getDetailId());

                detailDTO.setColors(productDetails.getColor());
                detailDTO.setSizes(productDetails.getSize());

                details.add(detailDTO);
            }

            dto.setImages(images);
            dto.setDetails(details);
            return dto;
        }
        return null;
    }

    @Override
    public Page<ProductAdminDTO> getAllProductsForAdmin(PageRequest pageRequest, String searchValue) {
        Specification<Product> spec = ProductSpecifications.hasSearchQuery(searchValue);
        Page<Product> products = productRepository.findAll(spec, pageRequest);

        return products.map(product -> {
            ProductAdminDTO productDTO = new ProductAdminDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setProductName(product.getProductName());
            productDTO.setBrandName(product.getBrand().getBrandName());
            productDTO.setCategoryName(product.getCategory().getCategoryName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());

            return productDTO;
        });
    }

    @Override
    public void delete(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    @Override
    public ProductDTO saveProduct(ProductRequest productRequest) {
        Product product;
        if (productRequest.getId() != null) {
            product = productRepository.findById(productRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        } else {
            product = new Product();
        }

        Brands brands = new Brands();
        brands.setBrandId(productRequest.getBrandId());

        Category category = new Category();
        category.setCategoryId(productRequest.getCategoryId());

        product.setProductName(productRequest.getName());
        product.setCategory(category);
        product.setBrand(brands);
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(savedProduct.getProductName());
        productDTO.setBrand(savedProduct.getBrand().getBrandName());
        productDTO.setCategory(savedProduct.getCategory().getCategoryName());
        productDTO.setPrice(savedProduct.getPrice());
        productDTO.setDescription(savedProduct.getDescription());

        return productDTO;
    }
}
