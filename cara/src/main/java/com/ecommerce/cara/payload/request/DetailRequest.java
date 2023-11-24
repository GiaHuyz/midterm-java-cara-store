package com.ecommerce.cara.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetailRequest {
    private Integer productId;
    private Integer detailId;
    private String color;
    private String size;
}
