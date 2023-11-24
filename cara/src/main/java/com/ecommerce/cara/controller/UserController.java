package com.ecommerce.cara.controller;

import com.ecommerce.cara.dto.CartDTO;
import com.ecommerce.cara.payload.ResponseData;
import com.ecommerce.cara.payload.request.PassChangeRequest;
import com.ecommerce.cara.payload.request.UserInfoRequest;
import com.ecommerce.cara.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<?> getUserInfoByUsername(@AuthenticationPrincipal UserDetails user) {
        ResponseData responseData = new ResponseData();
        try {
            responseData.setData(userService.getUserByUsername(user.getUsername()));
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("An error occurred while processing the request.");
            responseData.setData(null);
            System.out.println(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> updateUserInfoByUsername(@AuthenticationPrincipal UserDetails user, @RequestBody UserInfoRequest infoRequest) {
        ResponseData responseData = new ResponseData();
        if (infoRequest.getFullName() == null || infoRequest.getFullName().trim().isEmpty() ||
                infoRequest.getAddress() == null || infoRequest.getAddress().trim().isEmpty() ||
                infoRequest.getPhone() == null || infoRequest.getPhone().trim().isEmpty()) {
            responseData.setSuccess(false);
            responseData.setDescription("Full name, address, and phone must not be empty.");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        try {
            responseData.setData(userService.updateUserByUsername(user.getUsername(), infoRequest));
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("An error occurred while processing the request.");
            responseData.setData(null);
            System.out.println(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetails user, @RequestBody PassChangeRequest passChangeRequest) {
        ResponseData responseData = new ResponseData();
        if (userService.changePassword(user.getUsername(),
                passChangeRequest.getCurrentPassword(),
                passChangeRequest.getNewPassword())) {
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        responseData.setSuccess(false);
        responseData.setDescription("Password update failed");
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
}
