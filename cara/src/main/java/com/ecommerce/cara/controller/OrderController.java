package com.ecommerce.cara.controller;

import com.ecommerce.cara.payload.ResponseData;
import com.ecommerce.cara.payload.request.CartRequest;
import com.ecommerce.cara.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<?> addOrderFromUserCart(@AuthenticationPrincipal UserDetails user) {
        ResponseData responseData = new ResponseData();

        boolean result = orderService.addOrderFromUserCart(user.getUsername());

        if (result) {
            responseData.setDescription("Order added successfully.");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setSuccess(false);
            responseData.setDescription("An error occurred while processing the request.");
            responseData.setData(null);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getOrdersByUsername(@AuthenticationPrincipal UserDetails user) {
        ResponseData responseData = new ResponseData();
        responseData.setData(orderService.getOrdersByUsername(user.getUsername()));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
