package com.ecommerce.cara.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInfoRequest {
    private String fullName;
    private String address;
    private String phone;
}
