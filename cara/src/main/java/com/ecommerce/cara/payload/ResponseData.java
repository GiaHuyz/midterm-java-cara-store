package com.ecommerce.cara.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private boolean isSuccess = true;
    private String description;
    private Object data;
    private int totalPages;
}
