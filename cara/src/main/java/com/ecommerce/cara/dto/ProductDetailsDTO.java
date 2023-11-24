package com.ecommerce.cara.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter @Setter
public class ProductDetailsDTO {
    private int productId;

    private String productName;

    private double price;

    private String category;

    private String brand;

    private String description;

    private List<DetailDTO> details;

    private List<String> images;
}
