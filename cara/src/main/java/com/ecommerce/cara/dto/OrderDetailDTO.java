package com.ecommerce.cara.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDetailDTO {
    private String image;
    private String productName;
    private int quantity;
    private String color;
    private String size;
    private double price;
}
