package com.ecommerce.cara.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer categoryId;

    @NotNull
    private Integer brandId;

    @NotNull
    private Double price;

    @NotBlank
    private String description;
}
