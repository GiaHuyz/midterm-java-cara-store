package com.ecommerce.cara.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class OrderDTO {
    private int orderId;
    private String date;
    private double totalPrice;
    private List<OrderDetailDTO> orderDetails;
}
