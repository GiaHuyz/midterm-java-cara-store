package com.ecommerce.cara.service;

import com.ecommerce.cara.dto.CartDTO;
import com.ecommerce.cara.entity.Users;
import com.ecommerce.cara.payload.request.CartRequest;

import java.util.List;

public interface CartService {
    boolean addCartItems(List<CartRequest> cartItems, String username);
    List<CartDTO> getCartByUser(String username);
    void deleteCartItem(int cartItemId, String username);
}
