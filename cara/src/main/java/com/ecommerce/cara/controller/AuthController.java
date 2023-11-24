package com.ecommerce.cara.controller;

import com.ecommerce.cara.payload.ResponseData;
import com.ecommerce.cara.payload.request.LoginRequest;
import com.ecommerce.cara.payload.request.SignUpRequest;
import com.ecommerce.cara.service.LoginService;
import com.ecommerce.cara.utils.JwtUtilsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtilsHelper jwtUtilsHelper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        ResponseData responseData = new ResponseData();
        try {
            if (loginService.checkLogin(loginRequest)) {
                String token = jwtUtilsHelper.generateToken(loginRequest.getUsername());
                responseData.setData(token);
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            } else {
                responseData.setData(null);
                responseData.setDescription("Login failed. Username or password incorrect.");
                responseData.setSuccess(false);
                return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            responseData.setData(null);
            responseData.setDescription("An internal error occurred.");
            responseData.setSuccess(false);
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        if(loginService.existUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>("User is already exist!", HttpStatus.BAD_REQUEST);
        }
        ResponseData responseData = new ResponseData();
        if (loginService.addUser(signUpRequest)) {
            responseData.setData(true);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        responseData.setData(null);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
}
