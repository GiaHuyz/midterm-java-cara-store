package com.ecommerce.cara.repository;

import com.ecommerce.cara.entity.Cart;
import com.ecommerce.cara.entity.ProductDetails;
import com.ecommerce.cara.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUsersAndProductDetails(Users users, ProductDetails productDetail);
    List<Cart> findByUsersUsername(String username);
}
