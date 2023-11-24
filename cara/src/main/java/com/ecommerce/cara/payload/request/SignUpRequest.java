package com.ecommerce.cara.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpRequest {
    private String fullName;
    private String username;
    private String password;
    private String address;
    private String phone;
    private int roleId;
}
