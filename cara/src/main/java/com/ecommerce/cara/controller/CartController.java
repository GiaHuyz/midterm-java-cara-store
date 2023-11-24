package com.ecommerce.cara.controller;

import com.ecommerce.cara.dto.CartDTO;
import com.ecommerce.cara.payload.ResponseData;
import com.ecommerce.cara.payload.request.CartRequest;
import com.ecommerce.cara.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping()
    public ResponseEntity<?> addCart(@RequestBody List<@Valid CartRequest> cartItems, @AuthenticationPrincipal UserDetails user) {
        ResponseData responseData = new ResponseData();

        boolean result = cartService.addCartItems(cartItems, user.getUsername());

        if (result) {
            responseData.setDescription("Cart items added successfully.");
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
    public ResponseEntity<ResponseData> getCartByUser(@AuthenticationPrincipal UserDetails user) {
        ResponseData responseData = new ResponseData();
        try {
            List<CartDTO> result = cartService.getCartByUser(user.getUsername());

            responseData.setData(result);
            responseData.setDescription("Cart items fetched successfully.");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("An error occurred while processing the request.");
            responseData.setData(null);
            System.out.println(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserCart(@AuthenticationPrincipal UserDetails user, @PathVariable int id) {
        ResponseData responseData = new ResponseData();
        try {
            cartService.deleteCartItem(id, user.getUsername());
            responseData.setDescription("Cart item deleted successfully.");
        } catch (IllegalArgumentException e) {
            responseData.setSuccess(false);
            responseData.setDescription(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setDescription("An internal error occurred.");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
