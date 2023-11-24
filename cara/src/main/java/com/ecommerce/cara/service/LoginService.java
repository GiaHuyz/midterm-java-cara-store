package com.ecommerce.cara.service;

import com.ecommerce.cara.payload.request.LoginRequest;
import com.ecommerce.cara.payload.request.SignUpRequest;

public interface LoginService {
    boolean addUser(SignUpRequest signUpRequest);
    boolean existUsername(String username);
    boolean checkLogin(LoginRequest loginRequest);
}
