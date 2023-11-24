package com.ecommerce.cara.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductAdminDTO {
    private int productId;
    private String productName;
    private String description;
    private String categoryName;
    private String brandName;
    private double price;

}
