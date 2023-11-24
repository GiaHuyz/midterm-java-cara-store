package com.ecommerce.cara.controller;

import com.ecommerce.cara.payload.ResponseData;
import com.ecommerce.cara.service.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/brands")
public class BrandsController {

    @Autowired
    BrandsService brandsService;

    @GetMapping()
    public ResponseEntity<?> getAllBrands() {
        ResponseData responseData = new ResponseData();
        responseData.setData(brandsService.getAllBrands());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
