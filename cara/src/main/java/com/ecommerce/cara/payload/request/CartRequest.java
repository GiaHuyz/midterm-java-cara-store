package com.ecommerce.cara.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartRequest {
    @NotNull
    private int productId;
    @NotNull
    private int detailId;
    @NotNull
    private int quantity;
    @NotNull
    private double price;
}
