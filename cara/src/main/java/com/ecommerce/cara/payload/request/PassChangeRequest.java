package com.ecommerce.cara.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PassChangeRequest {
    private String currentPassword;
    private String newPassword;
}
