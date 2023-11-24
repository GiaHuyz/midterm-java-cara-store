package com.ecommerce.cara.dto;

import com.ecommerce.cara.entity.Product;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDTO {
    private int id;
    private String image;
    private String productName;
    private int quantity;
    private String color;
    private String size;
    private double price;
    private double subtotal;
}
