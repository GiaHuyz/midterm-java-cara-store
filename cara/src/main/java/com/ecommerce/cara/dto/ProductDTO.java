package com.ecommerce.cara.dto;

import com.ecommerce.cara.entity.Brands;
import com.ecommerce.cara.entity.Category;
import com.ecommerce.cara.entity.Images;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int productId;

    private String productName;

    private double price;

    private String category;

    private String brand;

    private String mainImage;

    private String description;
}
