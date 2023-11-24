package com.ecommerce.cara.service;

import com.ecommerce.cara.dto.OrderDTO;
import com.ecommerce.cara.entity.Order;

import java.util.List;

public interface OrderService {
    boolean addOrderFromUserCart(String username);
    List<OrderDTO> getOrdersByUsername(String username);
}
